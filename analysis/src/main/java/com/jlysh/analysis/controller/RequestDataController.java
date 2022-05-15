package com.jlysh.analysis.controller;

import com.jlysh.analysis.pojo.beans.UserInfo;
import com.jlysh.analysis.pojo.mapper.IsExistUserInfoMapper;
import com.jlysh.analysis.pojo.mapper.UserLoginMappler;
import com.jlysh.analysis.pojo.mapper.UserRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 江冷易水寒
 * @data 2021/4/20 14:33
 */
@RestController
public class RequestDataController {
    @Autowired
    UserRegistrationMapper userRegistrationMapper;
    @Autowired
    IsExistUserInfoMapper isExistUserInfoMapper;
    @Autowired
    UserLoginMappler userLoginMappler;

    //注册
    @PostMapping("/requestRegister")
    public boolean requestRegister(@RequestBody UserInfo user) {
        //先判断数据库中是否存在该数据
        Boolean isExistUserInfo = isExistUserInfoMapper.isExistUserInfo(user.getNum());
//          System.out.println(isExistUserInfo);
        if (isExistUserInfo) { //如果user在数据库中存在则返回false
            System.out.println(user.toString());
            return false;
        } else {
            //将数据存入map
            userRegistrationMapper.saveToDataBase(user);
            System.out.println(user.toString());
            return true;
        }
    }
    //登陆
    @PostMapping("/requestLogin")
    public UserInfo requestLogin(@RequestBody UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo loginInfo = userLoginMappler.fetchFromDatabase(user.getNum());
            if (loginInfo == null) {
                return new UserInfo();
            }
            if (loginInfo.getNum().equals(user.getNum()) && loginInfo.getPwd().equals(user.getPwd())) {
                session.setAttribute("user_info", loginInfo);//session保存user的UserInfo对象
                return (UserInfo)session.getAttribute("user_info");
            } else {
                return new UserInfo();
            }
    }

}
