package com.example.cloudproviderhystrixpayment8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:08
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CloudProviderHystrixPayment8001Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudProviderHystrixPayment8001Application.class,args);
    }
}
