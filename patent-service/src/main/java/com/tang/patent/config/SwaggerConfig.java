package com.tang.patent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/10
 * @name SwaggerConfig
 * @description: Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    /**
     * name:开发者姓名
     * url:开发者网址
     * email:开发者邮箱
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("汤镇源", "http://47.115.40.50:8080/patent/index", "tzysz1997@163.com");
        return new ApiInfoBuilder()
                .title("专利资讯网API接口")//标题
                .description("用于调用查询后端api接口")//文档接口的描述
                .contact(contact)
                .version("1.0.0")//版本号
                .build();
    }
}
