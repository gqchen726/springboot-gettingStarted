package com.example.cloud.controller;

import com.example.commons.config.LogConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * @author: guoqing.chen01@hand-china.com 2021-08-02 19:39
 **/

@EnableBinding(Sink.class)
public class ReceiveMessageListenerController {
    private final Logger logger = LogConfig.getLogger(ReceiveMessageListenerController.class);
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        logger.info("1号消费者，------>接收到的消息: {}\t port: {}",message.getPayload(),serverPort);
    }
}
