package com.example.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-31 14:09
 **/
public class LogConfig {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz.getClass());
    }
}
