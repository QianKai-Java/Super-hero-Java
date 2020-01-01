package com.next.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信小程序用户对象")
public class MPWXUserBO {
    @ApiModelProperty(name = "whichMP")
    private Integer whichMP;

    public Integer getWhichMP() {
        return whichMP;
    }

    public void setWhichMP(Integer whichMP) {
        this.whichMP = whichMP;
    }
}
