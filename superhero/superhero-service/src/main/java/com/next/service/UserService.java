package com.next.service;

import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.bo.RegistLoginUserBo;

public interface UserService {
    /**
     * 查询用户是否注册过
     * @param openId
     * @return
     */
    Users queryUserForLoginByMPWX(String openId);

    /**
     * 微信注册
     * @param openId
     * @param mpwxUserBO
     * @return
     */
    Users saveUserMPWX(String openId, MPWXUserBO mpwxUserBO);

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Users queryUserForLogin(String username,String password);

    /**
     * 用户注册
     * @param registLoginUserBo
     * @return
     */
    Users saveUser(RegistLoginUserBo registLoginUserBo);
}
