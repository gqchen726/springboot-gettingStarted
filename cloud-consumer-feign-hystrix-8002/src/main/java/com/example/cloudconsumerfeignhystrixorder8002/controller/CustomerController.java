package com.example.cloudconsumerfeignhystrixorder8002.controller;

import com.example.cloudconsumerfeignhystrixorder8002.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 19:12
 **/

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @GetMapping("/ok/{id}")
    public String customerInfo_OK(@PathVariable("id") Integer id) {
        return customerService.paymentInfo_OK(id);
    }
    @GetMapping("/timeout/{id}")
    public String customerInfo_Timeout(@PathVariable("id") Integer id) {
        return customerService.paymentInfo_Timeout(id);
    }
}
