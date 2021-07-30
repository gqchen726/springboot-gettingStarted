package com.example.cloudconsumerfeignhystrixorder8002.controller;

import com.example.cloudconsumerfeignhystrixorder8002.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
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
@DefaultProperties(defaultFallback = "customerGlobalTimeoutHandler")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @GetMapping("/ok/{id}")
    public String customerInfo_OK(@PathVariable("id") Integer id) {
        return customerService.paymentInfo_OK(id);
    }
    @GetMapping("/timeout/{id}")
    /*@HystrixCommand(fallbackMethod = "customerInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String customerInfo_Timeout(@PathVariable("id") Integer id) {
        int i = 1/0;
        return customerService.paymentInfo_Timeout(id);
    }
    public String customerInfo_TimeoutHandler(Integer id) {
        return "我是消费者，对方系统繁忙或者运行报错,请稍后再试,id: "+id+"\t"+"(┬┬﹏┬┬)~";
    }
    public String customerGlobalTimeoutHandler() {
        return "Global异常处理信息,请稍后重试(┬┬﹏┬┬)~";
    }

}
