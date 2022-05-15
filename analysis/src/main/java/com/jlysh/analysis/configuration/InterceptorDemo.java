package com.jlysh.analysis.configuration;

import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 江冷易水寒
 * @data 2021/4/22 8:46
 */
public class InterceptorDemo implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user_info");
        if(userInfo!=null){
            return true;
        }else {
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }

    }
}
