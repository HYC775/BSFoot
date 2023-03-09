package com.example.bsfood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bsfood.mapper")
public class BSfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(BSfoodApplication.class, args);
    }

}
