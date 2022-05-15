# -*- coding: utf-8 -*-
import json
import re
from random import Random

import scrapy
from lxml import etree
from scrapy import Selector
from weibo.items import WeiboItem
from weibo.items import FansItem
from datetime import datetime
from datetime import timedelta
import sys
sys.path.append("../")
#本项目没有爬取follows，爬取流程：爬取用户信息-》爬取粉丝id-》爬取粉丝用户信息-》爬取粉丝的粉丝id-》爬取粉丝的粉丝的信息······


#负责爬取指定用户的信息
class WeibocnSpider(scrapy.Spider):
    name = 'weibocn'
    allowed_domains = ['weibo.cn']
    #爬虫入口-以新浪微博热榜官方微博账号为入口
    ids = {'1742566624'} #,'5151079814'
    url_template = 'https://weibo.cn/u/{id}?page={page}'
    fans_url_template = 'https://weibo.cn/{id}/fans?page={page}'
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
        for id in self.ids:
            yield scrapy.Request(url=self.url_template.format(id=id,page=1), meta={'id':id,'page':1},callback=self.parse_user)
    # SexAndArea = ''
    # net_name = ''
    # 此方法中的response相当于response = requests.get()
    #爬取用户的信息
    def parse_user(self, response):
        try:# print(response.text)

            # print('-----------OK?',result.get('ok'))
            # print('-----------data?',result.get('data'))

            user_item = WeiboItem()
            if response.meta['page'] == 1:
                SexAreaName = str(response.xpath("//div[@class='ut']").get('data')).replace('\xa0',
                                                                                                                  '').replace(
                ' ', '')

                # 正则表达式过滤
                # 匹配 姓名And性别And地区
                reg = '''[\u4e00-\u9fa5]{1}/[\u4e00-\u9fa5]{1,}'''
                s = re.compile(reg)
                SexAndArea = "".join(s.findall(SexAreaName)[0])
                # 匹配 姓名/性别/地区
                reg_name = '''ctt">[\u4e00-\u9fa5a-zA-Z0-9]{1,}'''
                ss = re.compile(reg_name)
                response.meta['net_name'] = "".join(ss.findall(SexAreaName)[0]).replace('ctt">', '')
                response.meta['sex'] = SexAndArea[:SexAndArea.rfind('/')]
                response.meta['area'] = SexAndArea[SexAndArea.rfind('/') + 1:]

            # single_response = response.xpath("/html/body/div[3]/table/tr/td[2]/div/span[1]/text()[2]").extract_first()
            for p in response.xpath("//div[@class='c'and @id]"):
                try:
                    transfrom = "".join(p.xpath("./div/text()").re(r'[\u4e00-\u9fa5]'))
                    article = "".join(p.xpath("./div/span[@class='ctt']")[0].xpath('string(.)').extract())
                    user_item['article'] = article
                    user_item['transfrom'] = transfrom
                    user_item['like'] = "".join(p.xpath("./div/a").re(r'赞\[[0-9]*?\]')).replace('赞[', '').replace(']', '')
                    user_item['transmit'] = "".join(p.xpath("./div/a").re(r'转发\[[0-9]*?\]')).replace('转发[', '').replace(']', '')
                    user_item['discuss'] = "".join(p.xpath("./div/a").re(r'评论\[[0-9]*?\]')).replace('评论[', '').replace(']', '')
                    time_from = "".join(p.xpath("./div/span[@class='ct']/text()").extract()).split("\xa0来自")
                    user_item['time'] = self.clear_date(time_from[0])
                    user_item['origin'] = time_from[1]
                    user_item['insertTime'] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                    user_item['person_id'] = response.meta['id']
                    # SexAndArea = ''.join(response.meta['single_response'])
                    # print('------------',single_response.xpath('/html/body/div[3]/table/tr/td[2]/div/span[1]/text()[1]'))
                    # self.net_name = str(response.xpath('/html/body/div[3]/table/tr/td[2]/div/span[1]/text()[1]').extract_first())
                    user_item['net_name'] = response.meta['net_name']
                    user_item['sex'] = response.meta['sex']
                    user_item['area'] = response.meta['area']


                    yield user_item #保存到数据库
                    #爬取用户的下一页信息
                    #如果想要爬取每一个用户的所有微博，可以在yield scrapy.Request中加上 'net_name': response.meta['net_name'],'id': response.meta['sex'],'area': response.meta['area']
                    page = response.meta['page']+1
                    yield scrapy.Request(url=self.url_template.format(id=response.meta['id'], page=page), meta={'id': response.meta['id'],'net_name': response.meta['net_name'],'sex': response.meta['sex'],'area': response.meta['area'], 'page': page+1},callback=self.parse_user)
                    #粉丝列表
                    yield scrapy.Request(url=self.fans_url_template.format(id=response.meta['id'],page=1), meta={'id':response.meta['id'],'page':1},callback=self.parse_fans)
                except Exception as e:
                    print(e)
                    continue
        except:
            pass
            # print(e)

    #爬取粉丝
    def parse_fans(self,response):
        try:
            item_fans = FansItem()
            fans_list = response.xpath('//td[@valign="top" and @style]/a[1]/@href').extract()
            fans_id_list = [i[-10:] for i in fans_list] #获取id列表
            item_fans['from_id'] = response.meta['id'] #用户id
            item_fans['fans_id_list'] = fans_id_list
            item_fans['person_id'] = response.meta['id']
            #将id保存到item-数据库中
            yield item_fans
            #通过fans_id来继续调用parse_user
            for id in fans_id_list:
                yield scrapy.Request(url=self.url_template.format(id=id, page=1),meta={'id':id,'page':1}, callback=self.parse_user)
            #爬取下一页fans
            page = response.meta['page']+1
            print('当前正在爬取第{}页'.format(page))
            yield scrapy.Request(url=self.fans_url_template.format(id=response.meta['id'],page=page),meta={'id':response.meta['id'],'page':page,},callback=self.parse_user)
        except Exception as e:
            print(e)
    #爬取用户关注的人
    def parse_follows(self,response):
        pass

    def clear_date(self,publish_time):
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