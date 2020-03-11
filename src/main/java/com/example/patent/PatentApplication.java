package com.example.patent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.patent.dao")
public class PatentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatentApplication.class, args);
    }

}
