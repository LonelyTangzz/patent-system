package com.tang.patent.logger;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/8.
 * @name: BaseSqlLogInterceptor
 * @description: 打印sql语句log
 */
@Configuration
public class MyBatisPlusConfiguration {

    @Bean
    public LogicSqlInjector logicSqlInjector() {
        return new LogicSqlInjector() {
            /**
             * 注入自定义全局方法
             */
            @Override
            public List<AbstractMethod> getMethodList() {
                List<AbstractMethod> methodList = super.getMethodList();
                methodList.add(new LogicDeleteByIdWithFill());
                return methodList;
            }
        };
    }


    /**
     * SQL打印
     *
     * @return
     */
    @Bean
    public Interceptor sqlPrintInterceptor() {
        return new BaseSqlLogInterceptor();
    }

    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
