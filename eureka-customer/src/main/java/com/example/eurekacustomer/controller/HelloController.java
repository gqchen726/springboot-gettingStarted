package com.example.eurekacustomer.controller;

import com.example.commons.entity.CommonResult;
import com.example.eurekacustomer.api.UserRemoteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 16:29
 **/

@RestController
@RequestMapping("/customer/payment")
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final static String PAYMENT_URL = "http://admin:admin@PAYMENT";
    private final static String EMPLOYEE_URL = "http://localhost:8081/employee";

    @Autowired
    UserRemoteClient userRemoteClient;

    @Resource
    RestTemplate restTemplate;

    @GetMapping("/get/{id}")
    @ResponseBody
    public CommonResult<Object> getOne(@PathVariable Long id) {
        CommonResult<Object> result = restTemplate.getForObject(PAYMENT_URL+"/payment/findById/"+id, CommonResult.class);
        return result;
    }

    @GetMapping("/create")
    @ResponseBody
    public CommonResult<Object> createOne(Object object) {
        CommonResult<Object> result = restTemplate.postForObject(PAYMENT_URL+"/create/",object, CommonResult.class);
        return result;
    }



    @GetMapping("/callHello/{id}")
    public Object callHello(@PathVariable Long id) {
        //return restTemplate.getForObject("http://localhost:8083/house/hello",String.class);
        //String result = restTemplate.getForObject("http://eureka-client-user-service/user/hello",String.class);
        Object result = userRemoteClient.hello(id);
        logger.info("调用结果：{}",result);
        return result;
    }


}
