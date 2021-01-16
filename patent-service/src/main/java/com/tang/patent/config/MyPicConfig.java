package com.tang.patent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/16
 * @name MyPicConfig
 * @description: 配置照片路径
 */
@Configuration
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/patentImg/**").addResourceLocations("file:E:/IntellJ Idea/patent-system/patentImg/");
        registry.addResourceHandler("/avatarPic/**").addResourceLocations("file:E:/IntellJ Idea/patent-system/avatarPic/");
    }

}
