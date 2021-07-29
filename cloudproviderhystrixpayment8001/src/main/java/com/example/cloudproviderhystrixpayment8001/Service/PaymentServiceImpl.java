package com.example.cloudproviderhystrixpayment8001.Service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-29 18:09
 **/

@Service
public class PaymentServiceImpl {
    public String paymentInfo_OK(Integer id) {
        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_OK,id: "+"\t"+"O(∩_∩)O哈哈~";
    }
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 3;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_OK,id: "+"\t"+"O(∩_∩)O哈哈~"+" 耗时"+timeNumber+"秒";
    }
}
