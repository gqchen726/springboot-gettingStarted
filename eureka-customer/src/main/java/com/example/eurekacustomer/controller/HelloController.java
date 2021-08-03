package com.example.eurekacustomer.controller;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Employee;
import com.example.commons.entity.Payment;
import com.example.eurekacustomer.api.PaymentRemoteClient;
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
@RequestMapping("/customer")
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final static String PAYMENT_URL = "http://admin:admin@PAYMENT/payment";
    private final static String EMPLOYEE_URL = "http://localhost:8081/employee";

    @Autowired
    UserRemoteClient userRemoteClient;

    @Resource
    PaymentRemoteClient paymentRemoteClient;

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

    @GetMapping("getForEntity/{id}")
    public CommonResult<Employee> getOneEntity(@PathVariable Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/findById/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(entity.getStatusCode().value(),"获取数据失败");
        }
    }


    /**
     * OpenFeign远程调用
     * @param id
     * @return
     */
    @GetMapping("/callHello/{id}")
    public Object callHello(@PathVariable Long id) {
        Object result = userRemoteClient.hello(id);
        logger.info("调用结果：{}",result);
        return result;
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public CommonResult<Payment> findById(@PathVariable Long id) {
        return paymentRemoteClient.findById(id);
    }

    /**
     * feign超时测试
     * 客户端默认等待时间为1秒,超时后报错
     * @return
     */
    @GetMapping("/timeout")
    public String paymentTimeout() {
        return paymentRemoteClient.paymentTimeout();
    }

    @GetMapping("/zipkin")
    public String zipkin() {
        return paymentRemoteClient.zipkin();
    }

}
