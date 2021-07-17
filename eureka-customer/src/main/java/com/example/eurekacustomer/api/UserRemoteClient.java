package com.example.eurekacustomer.api;

import com.example.eurekacustomer.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-15 19:47
 **/

@FeignClient(value = "provider"/*,configuration = FeignConfiguration.class*/)
public interface UserRemoteClient {
    @GetMapping("/employee/findById/{id}")
    Object hello(@PathVariable Long id);
}
