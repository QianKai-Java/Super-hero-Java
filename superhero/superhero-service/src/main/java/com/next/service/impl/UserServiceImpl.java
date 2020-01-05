package com.next.service.impl;

import com.next.mapper.UsersMapper;
import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.bo.RegistLoginUserBo;
import com.next.service.UserService;
import com.next.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private final static String USER_DEFAULT_FACE_IMAGE_URL = "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIs2r0zgqD67tRABtxVL1Y4ftp7GwFN8Tz1bUCHFN9jricMEfsCPkKwm1ib5hfhqKGnImuiaKibicAubQw/132";

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLoginByMPWX(String openId) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mpWxOpenId",openId);

        Users users = usersMapper.selectOneByExample(example);

        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users saveUserMPWX(String openId, MPWXUserBO mpwxUserBO) {
        String userId = sid.nextShort();
        Users users = new Users();
        users.setId(userId);
        users.setNickname(mpwxUserBO.getNickName());
        users.setAppQqOpenId(openId);
        users.setFaceImage(mpwxUserBO.getAvatarUrl());
        users.setBirthday("1991-07-09");
        users.setIsCertified(0);
        users.setRegistTime(new Date());

        usersMapper.insert(users);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users result = usersMapper.selectOne(users);

        if (result == null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        Users result = usersMapper.selectOne(users);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users saveUser(RegistLoginUserBo registLoginUserBo) {
        String userId = sid.nextShort();
        Users users = new Users();
        users.setId(userId);
        users.setUsername(registLoginUserBo.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(registLoginUserBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setNickname(registLoginUserBo.getUsername());
        users.setFaceImage(USER_DEFAULT_FACE_IMAGE_URL);
        users.setBirthday("1991-07-09");
        users.setIsCertified(0);
        users.setRegistTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserInfo(Users users) {
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfoById(users.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Users queryUserInfoById(String userId){
        return usersMapper.selectByPrimaryKey(userId);
    }
}
