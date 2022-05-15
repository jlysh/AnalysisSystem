package com.jlysh.analysis.controller;

import com.jlysh.analysis.pojo.beans.GlobalVariable;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 江冷易水寒
 * @data 2021/4/21 19:50
 */
@RestController

public class RequestSessionController {
    @RequestMapping(value = "/requestSession")
    public UserInfo requestSession(HttpServletRequest request){
        UserInfo infomations = (UserInfo) request.getSession().getAttribute("user_info");
        GlobalVariable.prefix = infomations.getNum();
        return infomations;
    }

    //销毁session
    @RequestMapping(value = "/destorySession")
    public void destorySession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/login");
    }
}
