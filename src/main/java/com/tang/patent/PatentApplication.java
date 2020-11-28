package com.tang.patent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @name PatentApplication
 * @author tangzy
 * @since 2020/11/10
 * @version 1.0
 * @description: 启动类
 */
@SpringBootApplication
@MapperScan("com.tang.patent.dao")
public class PatentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatentApplication.class, args);
    }

}
