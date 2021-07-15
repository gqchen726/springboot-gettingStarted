package com.example.eurekapayment.controller;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Payment;
import com.example.eurekapayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:59
 **/

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    String serverPort;

    @GetMapping("/findById/{id}")
    @ResponseBody
    public CommonResult<Payment> findById(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        return payment != null ?
                new CommonResult<Payment>(200,"查询成功,serverPort: "+serverPort,payment):
                new CommonResult<Payment>(201,"查询失败,serverPort: "+serverPort);
    }

    @PostMapping("/save")
    @ResponseBody
    public CommonResult<String> save(@RequestBody Payment payment) {
        int result = paymentService.save(payment);
        return result>0?
                new CommonResult<String>(200,"插入记录成功,serverPort: "+serverPort,"插入记录:"+result):
                new CommonResult<String>(201,"插入数据失败,serverPort: "+serverPort);
    }
}
