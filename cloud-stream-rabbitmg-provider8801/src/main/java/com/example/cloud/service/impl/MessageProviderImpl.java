package com.example.cloud.service.impl;

import com.example.cloud.service.IMessageProvider;
import com.example.commons.config.LogConfig;
import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * @author: guoqing.chen01@hand-china.com 2021-08-02 16:35
 **/

@EnableBinding(Source.class)//定义消息推送管道
public class MessageProviderImpl  implements IMessageProvider {

    private final Logger logger = LogConfig.getLogger(MessageProviderImpl.class);
    @Resource
    private MessageChannel output;
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        logger.info("******serial: {}",serial);
        return serial;
    }
}
