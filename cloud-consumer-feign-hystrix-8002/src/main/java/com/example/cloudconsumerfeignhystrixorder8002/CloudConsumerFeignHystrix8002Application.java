package com.example.cloudconsumerfeignhystrixorder8002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 19:04
 **/
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class CloudConsumerFeignHystrix8002Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerFeignHystrix8002Application.class,args);
    }
}
