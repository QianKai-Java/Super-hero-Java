package com.next.pojo;

public class BaseResultMap<T> {
    private Integer status;
    private String msg;
    private T data;

    public BaseResultMap(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

}
