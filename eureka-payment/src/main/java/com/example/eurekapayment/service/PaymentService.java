package com.example.eurekapayment.service;

import com.example.commons.entity.Payment;
import org.springframework.stereotype.Service;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:56
 **/

@Service
public interface PaymentService {
    Payment findById(Long id);

    int save(Payment payment);
}
