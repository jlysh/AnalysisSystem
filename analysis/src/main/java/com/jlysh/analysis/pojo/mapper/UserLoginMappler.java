package com.jlysh.analysis.pojo.mapper;

import com.jlysh.analysis.pojo.BaseClass.BaseMapper;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.stereotype.Repository;


/**
 * @author 江冷易水寒
 * @data 2021/4/27 9:51
 */

@Repository
public interface UserLoginMappler extends BaseMapper<UserInfo>{
    //从数据库中取出用户信息
    public UserInfo fetchFromDatabase(String num);

}
