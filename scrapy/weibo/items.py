# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy
import datetime

class WeiboItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    article = scrapy.Field()    #原创
    transfrom = scrapy.Field() #转发内容
    discuss = scrapy.Field()    #评论数
    like = scrapy.Field()   #点赞数
    transmit = scrapy.Field()   #转发数
    time = scrapy.Field()   #发表时间
    origin = scrapy.Field()    #来自的客户端
    insertTime = scrapy.Field() #获取当前时间
    person_id = scrapy.Field() #获取用户id
    net_name = scrapy.Field() #获取用户网名
    sex = scrapy.Field() #获取用户性别
    area = scrapy.Field() #获取用户地区

class FansItem(scrapy.Item):
    #define the fields for your fans item in here
    from_id = scrapy.Field() #粉丝所属id
    fans_id_list = scrapy.Field() #粉丝id
    person_id = scrapy.Field()

class KeywordItem(scrapy.Item):
    article = scrapy.Field()  # 原创
    discuss = scrapy.Field()  # 评论数
    time = scrapy.Field()  # 发表时间
    origin = scrapy.Field()  # 来自的客户端
    insertTime = scrapy.Field()  # 获取当前时间
    person_id = scrapy.Field()  # 获取用户id
    net_name = scrapy.Field()  # 获取用户网名
    sex = scrapy.Field()  # 获取用户性别
    area = scrapy.Field()  # 获取用户地区
    keyword = scrapy.Field()  # 获取搜索关键词