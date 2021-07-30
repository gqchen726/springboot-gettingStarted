package com.example.cloudconsumerfeignhystrixorder8002.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-30 09:42
 **/
@Component
public class CustomerFallbackService implements CustomerService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "---CustomerFallbackService fall back paymentInfo_OK---(┬┬﹏┬┬)~";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "---CustomerFallbackService fall back paymentInfo_Timeout---(┬┬﹏┬┬)~";
    }


}
