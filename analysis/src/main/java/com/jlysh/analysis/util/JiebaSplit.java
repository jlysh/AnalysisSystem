package com.jlysh.analysis.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.jlysh.analysis.pojo.beans.Topic;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

/**
 * @author 江冷易水寒
 * @data 2021/5/20 19:09
 */


@Configuration
public class JiebaSplit {
    public HashMap<String, Integer> getJiebaMap(List<Topic> sentences) throws IOException {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        HashMap<String, Integer> map = new HashMap<>();

        StringBuilder sentence = new StringBuilder();
        for(Topic s:sentences){
            sentence.append(s.getArticle());
        }
        //使用jieba分词 并进行词频统计
        List<String> process = segmenter.sentenceProcess(sentence.toString());

        //====加载停用词表 并去除停用词====
        File stopFile = ResourceUtils.getFile("classpath:data/hit_stopwords.txt");
        List<String> stopList = FileUtils.readLines(stopFile,"utf8");
        for(String s:stopList){
//            System.out.println("====" + s);
        }
        process.removeAll(stopList);
        //=============================

        for (String p : process) {
//      System.out.println(p);
            if (map.containsKey(p)) {
                map.put(p,map.get(p)+1);
            }else{
                map.put(p,1);
            }
        }
        return map;
    }
}
