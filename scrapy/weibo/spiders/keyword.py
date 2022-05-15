# -*- coding: utf-8 -*-
import json
import re
from random import Random
import sys
import scrapy
from lxml import etree
from scrapy import Selector
from weibo.items import KeywordItem
from datetime import datetime
from datetime import timedelta


# 关键词爬取-》关键词-》用户评论-》用户信息-》下一页用户评论-》用户信息·····

#使用方法 scrapy crawl keyword -a keyword='关键词'
# 负责爬取指定用户的信息
class KeywordSpider(scrapy.Spider):
    name = 'keyword'
    allowed_domains = ['weibo.cn']
    # 爬虫入口
    keyword_template = 'https://weibo.cn/search/mblog?hideSearchFrame=1&keyword={keyword}&page={page}'
    user_template = 'https://weibo.cn/u/{id}'


    def __init__(self, keyword):
        self.keyword = keyword

    def transform(self, cookies):
        cookie_dict = {}
        cookies = cookies.replace(' ', '')
        list = cookies.split(';')
        for i in list:
            keys = i.split('=')[0]
            values = i.split('=')[1]
            cookie_dict[keys] = values
        return cookie_dict

    # Override start_requests()
    # 此方法相当于 requests.get()方法
    def start_requests(self):
        yield scrapy.Request(url=self.keyword_template.format(keyword=self.keyword, page=1),
                             meta={'keyword': self.keyword, 'page': 1}, callback=self.parse_page)

    # 爬取页面信息
    def parse_page(self, response):
        try:
            # 获得页面中的用户信息
            for p in response.xpath("//div[@class='c'and @id]"):
                try:
                    keyword_dict = {}
                    user_id = str(p.xpath('./div/a[@class="nk"]/@href').extract_first()[-10:])
                    article = "".join(p.xpath("./div/span[@class='ctt']")[0].xpath('string(.)').extract())
                    keyword_dict['article'] = article
                    keyword_dict['discuss'] = "".join(p.xpath("./div/a").re(r'评论\[[0-9]*?\]')).replace('评论[',
                                                                                                  '').replace(']',
                                                                                                                   '')
                    print('==================',keyword_dict['article'])
                    time_from = "".join(p.xpath("./div/span[@class='ct']/text()").extract()).split("\xa0来自")
                    keyword_dict['time'] = self.clear_date(time_from[0])
                    keyword_dict['origin'] = time_from[1]
                    keyword_dict['insertTime'] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                    yield scrapy.Request(url=self.user_template.format(id=user_id),
                                         meta={'id': user_id, 'keyword_dict': keyword_dict}, callback=self.parse_user)

                except Exception as e:
                    print(e)
                    continue
            # 爬取下一页
            page = response.meta['page'] + 1
            yield scrapy.Request(url=self.keyword_template.format(keyword=response.meta['keyword'], page=page),
                                 meta={'keyword': response.meta['keyword'], 'page': page}, callback=self.parse_page)
        except Exception as e:
            print(e)

    # 此方法中的response相当于response = requests.get()
    # 爬取用户的信息
    def parse_user(self, response):
        try:
            user_item = KeywordItem()
            # SexAreaName = str(response.xpath("//span[@class='ctt'][1]/text()").extract_first())
            SexAreaName = str(response.xpath("//div[@class='ut']").get('data')).replace('\xa0',
                                                                                                                  '').replace(
                ' ', '')
            # 正则表达式过滤
            # 匹配 性别/地区
            reg = '''[\u4e00-\u9fa5]{1}/[\u4e00-\u9fa5]{1,}'''
            s = re.compile(reg)
            SexAndArea = "".join(s.findall(SexAreaName)[0])
            # 匹配 姓名
            reg_name = '''ctt">[\u4e00-\u9fa5a-zA-Z0-9]{1,}'''
            ss = re.compile(reg_name)
            net_name = "".join(ss.findall(SexAreaName)[0]).replace('ctt">', '')

            keyword_dict = response.meta['keyword_dict']
            user_item['net_name'] = net_name
            user_item['sex'] = SexAndArea[:SexAndArea.rfind('/')]
            user_item['area'] = SexAndArea[SexAndArea.rfind('/') + 1:]
            user_item['article'] = keyword_dict['article']
            user_item['discuss'] = keyword_dict['discuss']
            user_item['time'] = keyword_dict['time']
            user_item['origin'] = keyword_dict['origin']
            user_item['insertTime'] = keyword_dict['insertTime']
            user_item['person_id'] = response.meta['id']
            user_item['keyword'] = self.keyword

            yield user_item  # 保存到数据库

        except Exception as e:
            print(e)

    def clear_date(self, publish_time):
        if "刚刚" in publish_time:
            publish_time = datetime.now().strftime('%Y-%m-%d %H:%M')

        elif "分钟" in publish_time:
            minute = publish_time[:publish_time.find("分钟")]
            minute = timedelta(minutes=int(minute))
            publish_time = (
                    datetime.now() - minute).strftime(
                "%Y-%m-%d %H:%M")
        elif "今天" in publish_time:
            today = datetime.now().strftime("%Y-%m-%d")
            time = publish_time.replace('今天', '')
            publish_time = today + " " + time

        elif "月" in publish_time:
            year = datetime.now().strftime("%Y")
            publish_time = str(publish_time)

            publish_time = year + "-" + publish_time.replace('月', '-').replace('日', '')
        else:
            publish_time = publish_time[:16]

        return publish_time
