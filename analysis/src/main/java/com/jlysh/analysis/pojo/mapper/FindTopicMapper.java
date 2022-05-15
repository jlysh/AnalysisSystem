package com.jlysh.analysis.pojo.mapper;

import com.jlysh.analysis.pojo.BaseClass.BaseMapper;
import com.jlysh.analysis.pojo.beans.Topic;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 江冷易水寒
 * @data 2021/4/29 10:32
 */
@Repository
public interface FindTopicMapper extends BaseMapper{
    //通过topic关键字查询数据库
    List<Topic> findTopicFromDataBase(String topic);

}
