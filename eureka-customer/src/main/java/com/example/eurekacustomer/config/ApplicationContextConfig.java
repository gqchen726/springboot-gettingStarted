package com.example.eurekacustomer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * spring-cloud 使用RestTemplate发送Http请求调用其他服务
 * @author: guoqing.chen01@hand-china.com 2021-07-14 21:27
 **/

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }
}
