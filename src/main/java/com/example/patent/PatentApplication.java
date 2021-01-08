package com.example.patent;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.patent.dao",annotationClass = Mapper.class)
public class PatentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatentApplication.class, args);
    }

}
