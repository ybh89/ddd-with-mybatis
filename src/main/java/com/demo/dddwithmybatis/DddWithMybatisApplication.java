package com.demo.dddwithmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan
@SpringBootApplication
public class DddWithMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DddWithMybatisApplication.class, args);
	}

}
