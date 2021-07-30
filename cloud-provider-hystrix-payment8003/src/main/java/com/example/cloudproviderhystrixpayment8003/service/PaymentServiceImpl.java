package com.example.cloudproviderhystrixpayment8003.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:09
 **/

@Service
public class PaymentServiceImpl {
    public String paymentInfo_OK(Integer id) {
        int i = 1/0;
        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 5000;
//        int i = 1/0;
        try{
            TimeUnit.MILLISECONDS.sleep(timeNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O哈哈~"+" 耗时"+timeNumber+"秒";
//        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池: "+Thread.currentThread().getName()+"系统繁忙或者运行报错,请稍后再试,id: "+id+"\t"+"(┬┬﹏┬┬)~";
    }

    //====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//请求次数范围内失败率
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能为负数");
        }
        //hutool工具包
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号"+uuid;
    }
    public String paymentCircuitBreakerFallback(Integer id) {
        return "id 不能为负数，请稍后重试,(┬┬﹏┬┬) id: "+id;
    }
}
