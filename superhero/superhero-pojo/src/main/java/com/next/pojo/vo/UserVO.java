package com.next.pojo.vo;

public class UserVO {
    private String id;

    private String username;

    private String password;

    private String nickname;

    private String mpWxOpenId;

    private String appQqOpenId;

    private String appWxOpenId;

    private String appWeiboUid;

    private Integer sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMpWxOpenId() {
        return mpWxOpenId;
    }

    public void setMpWxOpenId(String mpWxOpenId) {
        this.mpWxOpenId = mpWxOpenId;
    }

    public String getAppQqOpenId() {
        return appQqOpenId;
    }

    public void setAppQqOpenId(String appQqOpenId) {
        this.appQqOpenId = appQqOpenId;
    }

    public String getAppWxOpenId() {
        return appWxOpenId;
    }

    public void setAppWxOpenId(String appWxOpenId) {
        this.appWxOpenId = appWxOpenId;
    }

    public String getAppWeiboUid() {
        return appWeiboUid;
    }

    public void setAppWeiboUid(String appWeiboUid) {
        this.appWeiboUid = appWeiboUid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String birthday;

    private String faceImage;

    private String token;
}
