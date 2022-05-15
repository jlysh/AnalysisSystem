package com.jlysh.analysis.pojo.beans;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 * @author 江冷易水寒
 * @data 2021/4/20 16:03
 */
@Data
public class UserInfo {
    private String num;
    private String name;
    private String pwd;
    private String phone;
    private String email;
    private boolean flag;
    private Integer id;
    @Override
    public String toString() {
        return "UserInfo{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
