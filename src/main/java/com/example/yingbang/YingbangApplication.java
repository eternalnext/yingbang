package com.example.yingbang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.yingbang")
@SpringBootApplication
@EnableScheduling
public class YingbangApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingbangApplication.class, args);
    }

}
