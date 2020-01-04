package com.next.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信小程序用户对象")
public class MPWXUserBO {

    @ApiModelProperty(name = "nickName",required = true)
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @ApiModelProperty(name = "avatarUrl", required = true)
    private String avatarUrl;

    @ApiModelProperty(name = "whichMP")
    private Integer whichMP;

    public Integer getWhichMP() {
        return whichMP;
    }

    public void setWhichMP(Integer whichMP) {
        this.whichMP = whichMP;
    }
}
