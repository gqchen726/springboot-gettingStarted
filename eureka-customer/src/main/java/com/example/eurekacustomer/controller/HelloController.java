package com.example.eurekacustomer.controller;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Employee;
import com.example.eurekacustomer.api.UserRemoteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("getForEntity/{id}")
    public CommonResult<Employee> getOneEntity(@PathVariable Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(entity.getStatusCode().value(),"获取数据失败");
        }
    }



    @GetMapping("/callHello/{id}")
    public Object callHello(@PathVariable Long id) {
        Object result = userRemoteClient.hello(id);
        logger.info("调用结果：{}",result);
        return result;
    }


}
