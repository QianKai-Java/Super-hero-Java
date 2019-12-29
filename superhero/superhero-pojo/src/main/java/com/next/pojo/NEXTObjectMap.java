package com.next.pojo;

import io.swagger.annotations.ApiModelProperty;

public class NEXTObjectMap {

    @ApiModelProperty(value = "业务响应状态")
    private Integer status;

    @ApiModelProperty(value = "业务响应消息")
    private String msg;

    @ApiModelProperty(value = "业务响应数据")
    private Object data;

    public NEXTObjectMap(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public NEXTObjectMap(Object data) {
        this.status = 200;
        this.msg = "success";
        this.data = data;
    }

    public static NEXTObjectMap ok(Object data){
        return new NEXTObjectMap(data);
    }

    public static NEXTObjectMap ok(){
        return new NEXTObjectMap(null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
