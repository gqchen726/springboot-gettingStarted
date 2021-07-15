package com.example.eurekacustomer.controller;

import com.example.commons.entity.CommonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 16:29
 **/

@RestController
@RequestMapping("/customer/payment")
public class HelloController {
    private final static String PAYMENT_URL = "http://localhost:8083/payment";
    private final static String EMPLOYEE_URL = "http://localhost:8081/employee";

    @Resource
    RestTemplate restTemplate;

    @GetMapping("/get/{id}")
    @ResponseBody
    public CommonResult<Object> getOne(@PathVariable Long id) {
        CommonResult<Object> result = restTemplate.getForObject(PAYMENT_URL+"/findById/"+id, CommonResult.class);
        return result;
    }

    @GetMapping("/create")
    @ResponseBody
    public CommonResult<Object> createOne(Object object) {
        CommonResult<Object> result = restTemplate.postForObject(PAYMENT_URL+"/create/",object, CommonResult.class);
        return result;
    }


}
