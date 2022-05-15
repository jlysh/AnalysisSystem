package com.jlysh.analysis.service;

import com.jlysh.analysis.util.InvokingScrapy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author 江冷易水寒
 * @data 2021/5/21 18:22
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {

    @Autowired
    InvokingScrapy invokingScrapy;
    //3.添加定时任务 每天早上九点执行一次
    @Scheduled(cron = "0 0 9 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws IOException {
        log.info("执行静态定时任务时间: " + LocalDateTime.now());
        log.info("正在执行定时爬虫任务ing: " );
        invokingScrapy.invokingScrapyWeibocn();
        log.info("定时爬虫任务执行完成: " );

    }
}
