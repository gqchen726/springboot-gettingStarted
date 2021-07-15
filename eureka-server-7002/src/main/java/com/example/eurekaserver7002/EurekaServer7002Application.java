package com.example.eurekaserver7002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-15 09:37
 **/

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer7002Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7002Application.class,args);
    }
}
