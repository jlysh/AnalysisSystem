package com.jlysh.analysis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 江冷易水寒
 * @data 2021/5/21 18:26
 */

@Slf4j
@Configuration
public class InvokingScrapy {

    //调用综合爬虫
    public void invokingScrapyWeibocn() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("scrapy runspider",
                resolvePythonScriptPath("weibocn.py"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        log.info(results.toString());
    }

    //调用关键词爬虫
    public void invokingScrapyKeyword(String keyword) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("scrapy","runspider",
                resolvePythonScriptPath("keyword.py"),"-a","keyword=" + keyword);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        log.info(results.toString());
    }


    private static String resolvePythonScriptPath(String filename) {
        File file = new File("../scrapy/weibo/spiders/" + filename);
        return file.getAbsolutePath();
    }

    private static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }
}
