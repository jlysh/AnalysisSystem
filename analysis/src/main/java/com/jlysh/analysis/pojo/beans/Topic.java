package com.jlysh.analysis.pojo.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 江冷易水寒
 * @data 2021/4/29 10:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Topic {
    private int id;//id唯一自增长    /暂时无用
    private String article;//文章内容
    private int discuss;//评论数
    private String insertTime;//数据插入时间
    private String origin;//使用的客户端
    private Long person_id;//用户id
    private String time;//用户发表时间
    private int transmit;//转发次数
    private String net_name;//网名
    private String sex;//性别
    private String area;//地区
    private String emotion;//情感倾向
    private String keyword;//关键词
    private String transfrom;//转发内容
    private String like; //点赞数


    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }


    public String toString_in() {
        return
                article + "," +
                discuss + "," +
                insertTime + "," +
                origin + "," +
                person_id + "," +
                time + "," +
                transmit + "," +
                net_name + "," +
                sex + "," +
                area + "," +
                transfrom + "," +
                like
                ;
    }

    public String toString_out() {
        return
                        article + "," +
                        discuss + "," +
                        insertTime + "," +
                        origin + "," +
                        person_id + "," +
                        time + "," +
                        transmit + "," +
                        net_name + "," +
                        sex + "," +
                        area + "," +
                        transfrom + "," +
                        like + "," +
                        (Float.parseFloat(emotion)>0?"积极":"消极");
    }

    public String toString_in_keyword() {
        return
                        article + "," +
                        discuss + "," +
                        insertTime + "," +
                        origin + "," +
                        person_id + "," +
                        time + "," +
                        net_name + "," +
                        sex + "," +
                        keyword + "," +
                        area;
    }

    public String toString_out_keyword() {
        return
                        article + "," +
                        discuss + "," +
                        insertTime + "," +
                        origin + "," +
                        person_id + "," +
                        time + "," +
                        net_name + "," +
                        sex + "," +
                        keyword + "," +
                        area + "," +
                        (Float.parseFloat(emotion)>0?"积极":"消极");
    }
}
