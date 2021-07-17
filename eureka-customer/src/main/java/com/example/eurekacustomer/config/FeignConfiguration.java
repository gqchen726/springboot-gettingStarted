package com.example.eurekacustomer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring-cloud 使用Feign发送Http请求调用其他服务
 * @author: guoqing.chen01@hand-china.com 2021-07-16 13:34
 **/

@Configuration
public class FeignConfiguration {

    /**
     * 定义Feign的日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
