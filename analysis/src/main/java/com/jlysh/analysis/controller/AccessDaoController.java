package com.jlysh.analysis.controller;

import com.jlysh.analysis.pojo.beans.UserInfo;
import com.jlysh.analysis.pojo.mapper.UserLoginMappler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 江冷易水寒
 * @data 2021/4/27 15:44
 */

@RestController
public class AccessDaoController {

    @Autowired
    UserLoginMappler userLoginMappler;
    //从session中取出num与数据库中num进行比对，取出相应数据
    @RequestMapping("/fetchData")
    public void fetchData(HttpServletRequest request){
        UserInfo usr = (UserInfo) request.getSession().getAttribute("user_info");
        String num = usr.getNum();
        UserInfo uuu = userLoginMappler.fetchFromDatabase(num);
    }
}

