package com.example.cloudconfigclient3366.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: guoqing.chen01@hand-china.com 2021-08-02 13:54
 **/

@RestController
@RequestMapping("/config3366")
@RefreshScope
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/getInfo")
    public Object getInfo() {
        return configInfo;
    }
}
