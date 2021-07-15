package com.example.eurekapayment.service.impl;

import com.example.commons.entity.Payment;
import com.example.eurekapayment.mapper.PaymentMapper;
import com.example.eurekapayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:56
 **/
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentMapper paymentMapper;
    @Override
    public Payment findById(Long id) {
        return paymentMapper.findById(id);
    }

    @Override
    public int save(Payment payment) {
        return paymentMapper.save(payment);
    }
}
