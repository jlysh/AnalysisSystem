package com.jlysh.analysis.pojo.mapper;

import com.jlysh.analysis.pojo.BaseClass.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 江冷易水寒
 * @data 2021/4/28 10:56
 */
@Repository
public interface IsExistUserInfoMapper extends BaseMapper{
    public Boolean isExistUserInfo(String num);

}
