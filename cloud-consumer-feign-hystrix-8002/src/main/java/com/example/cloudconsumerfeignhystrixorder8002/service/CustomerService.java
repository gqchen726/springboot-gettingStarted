package com.example.cloudconsumerfeignhystrixorder8002.service;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 19:08
 **/
@Service
@FeignClient(name = "payment-hystrix", path = "/payment/hystrix", fallback = CustomerFallbackService.class)
public interface CustomerService {
    @GetMapping("/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);
    @GetMapping("/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable("id") Integer id);
}
