package com.jlysh.analysis.service;

import com.alibaba.fastjson.JSON;
import com.jlysh.analysis.pojo.beans.GlobalVariable;
import com.jlysh.analysis.pojo.beans.Topic;
import com.jlysh.analysis.pojo.beans.UserInfo;
import com.jlysh.analysis.pojo.mapper.FindInformationMapper;
import com.jlysh.analysis.pojo.mapper.FindTopicMapper;
import com.jlysh.analysis.util.InvokingPython;
import com.jlysh.analysis.util.InvokingScrapy;
import io.lettuce.core.SetArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 江冷易水寒
 * @data 2021/4/29 8:45
 *
 * 话题舆情分析
 *
 */
@Slf4j
@RestController
@RequestMapping("/service")
public class ReportService {
    @Autowired
    FindTopicMapper findTopicMapper;
    @Autowired
    FindInformationMapper findInformationMapper;
    @Autowired
    TopicFileWriter toipicFileWriter;
    @Autowired
    InvokingPython invokingPython;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    InvokingScrapy invokingScrapy;
    //通过topic查询数据库并返回结果，关键词(keyword)查询

    public List<Topic> topicReportService(String topic) throws IOException {
        String csv_prefix = GlobalVariable.prefix;
        System.out.println("=======================关键词舆情分析ing=======================");
        System.out.println("=======================用户--" + csv_prefix + "=======================");
        System.out.println("topic:===" + topic);
        if("".equals(topic) || topic == null) {
            log.info("====================关键词keyword为空=========================");
            return new ArrayList<>();
        }
        //关键字结果集+
        List<Topic> topics = findTopicMapper.findTopicFromDataBase(topic);
        System.out.println("ttt-----------" + topics.size());
        List<Topic> topReslts;
        //如果关键词结果集在数据库中没有，则进行爬取
//        if(topics.size() == 0){
//            //调用Scrapy程序-爬取数据，并再次从数据库中获取该关键词结果集
//            log.info("*****************正在从微博上爬取关键词为: "+GlobalVariable.keyword+" 的数据*****************");
//            invokingScrapy.invokingScrapyKeyword(GlobalVariable.keyword);
//            log.info("*****************数据爬取完成*********************");
//            topics = findTopicMapper.findTopicFromDataBase(topic);
//        }

        //写入到in文件
        toipicFileWriter.keyword_fileWriter(topics);
        log.info("*****************正在用机器学习进行分类*****************");
        //判断redis中是否有数据，如果有直接取，redis中key的生产周期设置为1h
        String s = (String) redisTemplate.opsForValue().get(topic);
        List<Topic> redisTopic = JSON.parseArray(s, Topic.class);
        if( redisTopic!=null) {
            log.info("*****************从Redis服务器中获取*****************");
            topReslts = redisTopic;
        }else{
            //调用python机器学习分类程序 参数：前缀，选择分类方式（0:keyword分类，1：综合分类）
            log.info("*****************正在进行情感分类*****************");
            invokingPython.invokingPython(csv_prefix,"0");
            log.info("*****************机器训练完成，以生成comments_out_keyword.csv文件*****************");
            //读取out文件
            topReslts = toipicFileWriter.keyword_fileReader();
            //将tops数据保存到redis中 设置key过期时间为1小时
            redisTemplate.opsForValue().set(topic, JSON.toJSONString(topReslts),10, TimeUnit.MINUTES);
        }

        for (Topic t : topReslts) {
            log.info("------------" + t.toString_out_keyword() + "---------------------");
        }
        log.info("============================================");

        return topReslts;
    }

    //综合查询 -- redis过期时间为10分钟
    public List<Topic> synthesisReportService() throws IOException {
        String csv_prefix = GlobalVariable.prefix;
        System.out.println("=======================综合舆情分析ing=======================");
        System.out.println("=======================用户--" + csv_prefix + "=======================");

        //综合舆情结果集
        List<Topic> topics = findInformationMapper.findInformationFromDataBase();
        List<Topic> topReslts = null;
        //写入到in文件
        toipicFileWriter.fileWriter(topics);
        log.info("*****************正在用机器学习进行分类*****************");
        //每隔10分钟更新一回
        //判断redis中是否有数据，如果有直接取，redis中key的生产周期设置为10分钟
        String s = (String) redisTemplate.opsForValue().get("synthesisReport");
        List<Topic> redisTopic = JSON.parseArray(s, Topic.class);
        if( redisTopic!=null) {
            log.info("*****************从Redis服务器中获取*****************");
            topReslts = redisTopic;
        }else{
            //调用python机器学习分类程序 参数：前缀，选择分类方式（0:keyword分类，1：综合分类）
            invokingPython.invokingPython(csv_prefix,"1");
            log.info("*****************机器训练完成，以生成comments_out.csv文件*****************");
            //读取out文件
            topReslts = toipicFileWriter.fileReader();
            //将tops数据保存到redis中 设置key过期时间为1小时
            redisTemplate.opsForValue().set("synthesisReport", JSON.toJSONString(topReslts),10, TimeUnit.MINUTES);

        }

        for (Topic t : topReslts) {
            log.info("------------" + t.toString_out() + "---------------------");
        }
        log.info("============================================");
        return topReslts;
    }
}
