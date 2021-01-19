package com.tang.patent;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @name PatentApplication
 * @author tangzy
 * @since 2021/1/16
 * @version 1.0
 * @description: Spring boot启动类
 */
@SpringBootApplication
@MapperScan(value = "com.tang.patent.dao",annotationClass = Mapper.class)
public class PatentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PatentApplication.class, args);
    }

}
