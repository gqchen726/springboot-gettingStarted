package com.example.cloudproviderhystrixpayment8003.controller;

import com.example.cloudproviderhystrixpayment8003.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:15
 **/

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Resource
    PaymentServiceImpl paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/hystrix/ok/{id}")
    public  String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        logger.info("*****result: {}",result);
        return result;
    }
    @GetMapping("/hystrix/timeout/{id}")
    public  String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_Timeout(id);
        logger.info("*****result: {}",result);
        return result;
    }

    //====服务熔断

    @GetMapping("/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) throws Exception {
        String result = paymentService.paymentCircuitBreaker(id);
        logger.info("******result: {}",result);
        return result;
    }
}
