package com.example.commons.entity;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 18:37
 **/

public class CommonResult<T> {
    Integer statusCode;
    String  message;
    T       data;

    public CommonResult() {
    }

    public CommonResult(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public CommonResult(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
