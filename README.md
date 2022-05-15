# AnalysisSystem
基于SVM的舆情分析系统

本程序设计包含三个文件夹和一个txt文件：
				analysis文件夹：该文件夹下存放的是Springboot项目，用来进行WEB页面的展示。
				scrapy文件夹：该文件夹下存放的是爬虫项目，用来爬取新浪微博项目，使用的是Python的Scrapy框架。
				svm文件夹：该文件夹下存放的是机器学习项目，用来进行微博文本内容的情感分类，使用的是支持向量机模型。
				mysql文件夹：该文件夹下存放的是本设计的数据库表结构，附带测试数据。
				flume.txt文件：存放flume启动命令

mysql用户名和密码：
				用户名：root 
				密码:root

开发环境：
				Python开发工具：PyCharm Community Edition
				Java开发工具：IntelliJ IDEA
数据集：
				svm\data\negative.xlsx
				svm\data\positive.xlsx

安装运行环境：	
				Mysql版本：5.7.29 
				操作系统：windows 10操作系统 
				服务器：tomcat8.0服务器
				虚拟机：Vmware Workstation
				Hadoop：Hadoop2.8.5

特殊说明：由于svm/data目录下的训练集和训练模型文件有些庞大，故已删除。
				

