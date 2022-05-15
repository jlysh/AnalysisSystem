package com.jlysh.analysis.pojo.mapper;

import com.jlysh.analysis.pojo.BaseClass.BaseMapper;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author 江冷易水寒
 * @data 2021/4/28 10:32
 */
@Repository
public interface UserRegistrationMapper extends BaseMapper<UserInfo> {
    //保存用户信息到数据库
    void saveToDataBase(UserInfo userInfo);
}
