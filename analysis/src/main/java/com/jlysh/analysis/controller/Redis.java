//package com.jlysh.analysis.controller;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONPObject;
//import com.jlysh.analysis.pojo.beans.UserInfo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @Author GocChin
// * @Date 2021/5/6 13:07
// * @Blog: itdfq.com
// * @QQ: 909256107
// * @Descript:
// */
//
//
//@RestController
//@RequestMapping("/redis")
//public class Redis {
//
//    /**
//     * redisTemplate.opsForValue();//操作字符串
//     * redisTemplate.opsForHash();//操作hash
//     * redisTemplate.opsForList();//操作list
//     * redisTemplate.opsForSet();//操作set
//     * redisTemplate.opsForZSet();//操作有序set
//     */
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @GetMapping
//    public UserInfo testRedis() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("江冷易水寒");
//        userInfo.setEmail("ma576642386@163.com");
//        userInfo.setId(111521);
//        //设置值到redis
////        redisTemplate.opsForValue().set("name","韩信");
//        redisTemplate.opsForValue().set("use",JSON.toJSONString(userInfo));
//        //从redis获取值
//        String name = (String) redisTemplate.opsForValue().get("uses");
////        System.out.println("---------" + name);
//        UserInfo userInfo1 = JSON.parseObject(name,UserInfo.class);
//        return userInfo1;
//    }
//}
//
