package com.example.eurekapayment.mapper;

import com.example.commons.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:24
 **/

@Mapper
public interface PaymentMapper {

    Payment findById(Long id);

    int save(Payment payment);

}
