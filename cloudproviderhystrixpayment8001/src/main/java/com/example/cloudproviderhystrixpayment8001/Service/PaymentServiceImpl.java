package com.example.cloudproviderhystrixpayment8001.Service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
}
