package com.jlysh.analysis;

import com.jlysh.analysis.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AnalysisApplication.class)
public class AnalysisApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(AnalysisApplication.class, args);
    }


}
