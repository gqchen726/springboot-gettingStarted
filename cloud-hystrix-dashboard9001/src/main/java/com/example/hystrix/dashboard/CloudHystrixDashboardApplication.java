package com.example.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-30 15:44
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
public class CloudHystrixDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudHystrixDashboardApplication.class, args);
    }


}
