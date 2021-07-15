package com.example.eurekacustomer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 21:27
 **/

@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }
}
