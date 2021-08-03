package com.example.cloud.gateway.filter;

import com.example.commons.config.LogConfig;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;


/**
 * @author: guoqing.chen01@hand-china.com 2021-07-31 14:05
 **/
@Configuration
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    private final Logger logger = LogConfig.getLogger(MyLogGatewayFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("**********come in MyLogGatewayFilter: {}",new Date());
        String ageStr = exchange.getRequest().getQueryParams().getFirst("age");
        Integer age = null;
        try {
            age = Integer.parseInt(ageStr);
        } catch (Exception e) {
            logger.info("***参数类型非法: {}",age);
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        if (age == null || age <= 12) {
            logger.info("***年龄非法: {}",age);
        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
