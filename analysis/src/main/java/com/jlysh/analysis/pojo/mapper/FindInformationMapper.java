package com.jlysh.analysis.pojo.mapper;

import com.jlysh.analysis.pojo.BaseClass.BaseMapper;
import com.jlysh.analysis.pojo.beans.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 江冷易水寒
 * @data 2021/5/18 14:46
 */
@Repository
public interface FindInformationMapper extends BaseMapper {
    //综合查询舆情信息
    List<Topic> findInformationFromDataBase();
}
