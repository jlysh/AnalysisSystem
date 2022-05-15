# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html

import pymysql
from weibo.items import WeiboItem
from weibo.items import FansItem
from weibo.items import KeywordItem

class WeiboPipeline(object):
    def __init__(self):
        #创建连接
        self.conn = pymysql.connect('localhost','root','root','analysis')
        #创建游标
        self.cursor = self.conn.cursor()
    #管道处理，将数据存入mysql
    def process_item(self, item, spider):
        try:
            #处理用户信息
            if isinstance(item,WeiboItem):
                sql = "INSERT INTO sys_corpus(`article`,`transfrom`,`discuss`,`like`,`transmit`,`time`,`origin`,`insertTime`,`person_id`,`net_name`,`sex`,`area`) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                print('==================================')
                self.cursor.execute(sql,(item['article'],item['transfrom'],item['discuss'],item['like'],item['transmit'],item['time'],item['origin'],item['insertTime'],item['person_id'],item['net_name'],item['sex'],item['area']))

            #处理粉丝信息
            if isinstance(item,FansItem):
                for fans_id in item['fans_id_list']:
                    sql = "INSERT INTO sys_corpus_fans(`person_id`,`fans_id`) VALUES (%s,%s)"
                    print('==================================')
                    self.cursor.execute(sql,(item['person_id'],fans_id))

            #处理关键字搜索信息
            if isinstance(item,KeywordItem):
                sql = "INSERT INTO sys_corpus_keyword(`keyword`,`article`,`discuss`,`time`,`origin`,`insertTime`,`person_id`,`net_name`,`sex`,`area`) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                print('==================================')
                self.cursor.execute(sql,(item['keyword'],item['article'],item['discuss'],item['time'],item['origin'],item['insertTime'],item['person_id'],item['net_name'],item['sex'],item['area']))
            self.conn.commit()
            return item
        except Exception as e:
            self.conn.rollback()
            print(e)
            # pass

    #关闭sqldb
    def close_spider(self,spider):
        self.cursor.close()
        self.conn.close()