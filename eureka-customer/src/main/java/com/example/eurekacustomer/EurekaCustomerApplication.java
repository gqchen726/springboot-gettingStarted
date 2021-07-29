package com.example.eurekacustomer;

import com.example.myRule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 16:27
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.example.eurekacustomer.api")
//替换开箱即用的轮询算法
//@RibbonClient(name = "PAYMENT",configuration = MySelfRule.class)
public class EurekaCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomerApplication.class, args);
    }

    /*@Bean
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }*/
}
