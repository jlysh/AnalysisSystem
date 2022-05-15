package com.jlysh.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@MapperScan("com.jlysh.analysis.pojo.mapper")
@SpringBootApplication
public class AnalysisApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnalysisApplication.class, args);
	}
}
