package com.exmple.cloudconfigcenter3344;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-31 15:15
 **/

@SpringBootApplication
@EnableConfigServer
public class CloudConfigCenter3344Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfigCenter3344Application.class,args);
    }
}
