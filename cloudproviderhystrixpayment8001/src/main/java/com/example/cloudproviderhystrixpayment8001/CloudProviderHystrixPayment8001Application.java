package com.example.cloudproviderhystrixpayment8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:08
 **/

@SpringBootApplication
@EnableCircuitBreaker
public class CloudProviderHystrixPayment8001Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudProviderHystrixPayment8001Application.class,args);
    }
}
