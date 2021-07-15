package com.example.commons.entity;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:22
 **/

public class Payment {
    Long id;
    String serial;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
