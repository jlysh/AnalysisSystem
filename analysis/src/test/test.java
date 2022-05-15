package com.jlysh.analysis.view;

import com.jlysh.analysis.AnalysisApplication;
import com.jlysh.analysis.pojo.beans.UserInfo;

import com.jlysh.analysis.pojo.mapper.UserMappler;
import org.junit.Test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author 江冷易水寒
 * @data 2021/4/21 10:33
 */

@SpringBootTest(classes = { AnalysisApplication.class })
@MapperScan("com.jlysh.analysis.pojo.mapper")
public class test {

    @Autowired
    UserMappler userMappler;
    @Test
    public void initData(){
        SimulationUserData data = new SimulationUserData();
        UserInfo user = new UserInfo();
        UserInfo user1 = new UserInfo();
        //模拟数据
        user.setNum("aa");
        user.setPwd("123");
        user.setPhone("123");
        user.setName("jlysh");
        user.setEmail("1231@qq.com");
        data.setMap(user.getNum(),user);

        user1.setNum("bb");
        user1.setPwd("123");
        user1.setPhone("122223");
        user1.setName("jlysh");
        user1.setEmail("1231@qq.com");
//        data.setMap(user1.getNum(),user1);

//        userMappler.saveToDataBase(user);
        UserInfo uuu = userMappler.fetchFromDatabase("123"); //被拦截器拦住了
        System.out.println(uuu.toString());
//        Map<String,UserInfo> mm = data.getMap();
////        System.out.println("123");
//        System.out.println(mm.get("aa").getPhone());

    }
}
