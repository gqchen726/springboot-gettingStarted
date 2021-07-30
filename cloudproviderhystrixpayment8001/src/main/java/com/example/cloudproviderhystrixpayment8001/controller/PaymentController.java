package com.example.cloudproviderhystrixpayment8001.controller;

import com.example.cloudproviderhystrixpayment8001.Service.PaymentServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

}
