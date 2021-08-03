package com.example.cloudconfigclient3355;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: guoqing.chen01@hand-china.com 2021-08-02 09:31
 **/

@SpringBootApplication
@EnableEurekaClient
public class CloudConfigClient3355Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfigClient3355Application.class,args);
    }
}
