package com.itheima.order.domain;

public class Order {
    private String id;
    private String msg;

    @Override
    public String toString() {
        return "Order{ id='" + id + ", msg='" + msg + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
