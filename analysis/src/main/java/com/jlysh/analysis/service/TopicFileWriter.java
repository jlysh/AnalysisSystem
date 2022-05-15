package com.jlysh.analysis.service;

import com.jlysh.analysis.constants.SynthesisEnum;
import com.jlysh.analysis.constants.TopicsEnum;
import com.jlysh.analysis.pojo.beans.GlobalVariable;
import com.jlysh.analysis.pojo.beans.Topic;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 江冷易水寒
 * @data 2021/4/30 8:43
 */


@Configuration
public class TopicFileWriter {

    //综合舆情分析写入到csv_in文件中
    public Boolean fileWriter(List<Topic> topics) {
        try {
            //将结果集保存到本地文件
            //csv_out、csv_in文件前缀
            String csv_prefix = GlobalVariable.prefix;
            File file = new File(csv_prefix + "_comments_in.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedReader = new BufferedWriter(fileWriter);
            //article,discuss,insertTime,origin,person_id,time,transmit,net_name,sex,area,transfrom,like;
            bufferedReader.write(StringUtils.join(SynthesisEnum.values(), ","));
            bufferedReader.newLine();
            for (Topic t : topics) {
                bufferedReader.write(t.toString_in());
                bufferedReader.newLine();
            }
            bufferedReader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Topic> fileReader() throws IOException {
        //csv_out、csv_in文件前缀
        String csv_prefix = GlobalVariable.prefix;
        //用户id + 文件名
        System.out.println("==============="+  csv_prefix);
        File file = new File(csv_prefix + "_comments_out.csv");
        List<Topic> topics = new ArrayList<>();
        if (!file.exists()) {
            file.createNewFile();
        }
        FileReader fileReader = new FileReader(file.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String topic = null;
        bufferedReader.readLine();
        while ((topic = bufferedReader.readLine()) != null) {
            try {
                String[] splitLine = topic.split(",");
                Topic t = new Topic();
                t.setArticle(splitLine[1]);
                t.setDiscuss(Integer.parseInt(splitLine[2]));
                t.setInsertTime(splitLine[3]);
                t.setOrigin(splitLine[4]);
                t.setPerson_id(Long.parseLong(splitLine[5]));
                t.setTime(splitLine[6]);
                t.setTransmit(Integer.parseInt(splitLine[7]));
                t.setNet_name(splitLine[8]);
                t.setSex(splitLine[9]);
                t.setArea(splitLine[10]);
                t.setTransfrom(splitLine[11]);
                t.setLike(splitLine[12]);
                t.setEmotion(splitLine[13]);
                topics.add(t);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return topics;
    }


    //关键字舆情分析写入到csv_in文件中
    public Boolean keyword_fileWriter(List<Topic> topics) {
        try {
            //csv_out、csv_in文件前缀
            String csv_prefix = GlobalVariable.prefix;
            //将结果集保存到本地文件
            System.out.println("==============="+  csv_prefix);
            File file = new File(csv_prefix + "_comments_in_keyword.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedReader = new BufferedWriter(fileWriter);
            //article,discuss,insertTime,origin,person_id,time,net_name,sex,area,keyword;
            bufferedReader.write(StringUtils.join(TopicsEnum.values(), ","));
            bufferedReader.newLine();
            for (Topic t : topics) {
                bufferedReader.write(t.toString_in_keyword());
                bufferedReader.newLine();
            }
            bufferedReader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Topic> keyword_fileReader() throws IOException {
        //csv_out、csv_in文件前缀
        String csv_prefix = GlobalVariable.prefix;
        //用户id + 文件名
        File file = new File(csv_prefix + "_comments_out_keyword.csv");
        List<Topic> topics = new ArrayList<>();
        if (!file.exists()) {
            file.createNewFile();
        }
        FileReader fileReader = new FileReader(file.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String topic = null;
        bufferedReader.readLine();
        while ((topic = bufferedReader.readLine()) != null) {
            try {
                String[] splitLine = topic.split(",");
                Topic t = new Topic();
                t.setArticle(splitLine[1]);
                t.setDiscuss(Integer.parseInt(splitLine[2]));
                t.setInsertTime(splitLine[3]);
                t.setOrigin(splitLine[4]);
                t.setPerson_id(Long.parseLong(splitLine[5]));
                t.setTime(splitLine[6]);
                t.setNet_name(splitLine[7]);
                t.setSex(splitLine[8]);
                t.setKeyword(splitLine[9]);
                t.setArea(splitLine[10]);
                t.setEmotion(splitLine[11]);
                topics.add(t);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return topics;
    }


}
