package com.example.eurekacustomer.api;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Payment;
import com.example.eurekacustomer.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 15:48
 **/
@Component
@FeignClient(value = "PAYMENT", path = "/payment")
public interface PaymentRemoteClient {
    @GetMapping("/findById/{id}")
    CommonResult<Payment> findById(@PathVariable Long id);
    @GetMapping("/timeout")
    String paymentTimeout();
}
