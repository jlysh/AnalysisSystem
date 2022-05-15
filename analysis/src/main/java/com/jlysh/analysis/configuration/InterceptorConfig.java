package com.jlysh.analysis.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 江冷易水寒
 * @data 2021/4/22 8:43
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 注册用户登陆拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new InterceptorDemo());
        interceptorRegistration.addPathPatterns("/*");
        interceptorRegistration.excludePathPatterns(
                "/requestLogin",
                "/requestRegister",
                "/index",//首页
                "/",//首页
                "/login",//注册
                "/reg",//登录
                "/assets/**",
                "/css/**",
                "/images/**",
                "/js/**",
                "/layui/**"
        );
    }
}
