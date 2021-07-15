package com.example.eurekapayment.controller;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Payment;
import com.example.eurekapayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:59
 **/

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/findById/{id}")
    @ResponseBody
    public CommonResult<Payment> findById(@PathVariable Long id) {
        return new CommonResult<Payment>(200,"success",paymentService.findById(id));
    }

    @PostMapping("/save")
    @ResponseBody
    public CommonResult<String> save(@RequestBody Payment payment) {
        int result = paymentService.save(payment);
        return result>0?
                new CommonResult<String>(200,"插入记录成功","插入记录:"+result):
                new CommonResult<String>(200,"插入数据失败");
    }
}
