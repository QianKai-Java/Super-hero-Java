package com.next.api.controller;


import com.next.pojo.NEXTJSONResult;
import com.next.pojo.NEXTObjectMap;
import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.bo.ModifiedUserBO;
import com.next.pojo.bo.RegistLoginUserBo;
import com.next.pojo.vo.UserVO;
import com.next.pojo.vo.WXSessionVo;
import com.next.service.MovieService;
import com.next.service.StaffService;
import com.next.service.UserService;
import com.next.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(value = "用户相关接口", tags = {"用户相关接口"})
@RequestMapping(value = "/user")
public class UsersController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "用户退出登录", httpMethod = "POST")
    public NEXTJSONResult logout(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        return NEXTJSONResult.ok();
    }

    @RequestMapping(value = "/registOrLogin", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册和登录", httpMethod = "POST")
    public NEXTJSONResult registerOrLogin(@RequestBody RegistLoginUserBo registLoginUserBo) {
        if (StringUtils.isBlank(registLoginUserBo.getPassword()) || StringUtils.isBlank(registLoginUserBo.getUsername())) {
            return NEXTJSONResult.errorMsg("用户名或密码不能为空");
        }

        Users user = null;
        boolean isExist = userService.queryUsernameIsExist(registLoginUserBo.getUsername());

        if (isExist) {
            try {
                user = userService.queryUserForLogin(registLoginUserBo.getUsername(),
                        MD5Utils.getMD5Str(registLoginUserBo.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            user = userService.saveUser(registLoginUserBo);

        }

        UserVO userVO = setRedisUserToken(user);
        return NEXTJSONResult.ok(userVO);
    }

    @RequestMapping(value = "/uploadFace", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    @ApiOperation(value = "上传头像", httpMethod = "POST")
    public NEXTJSONResult uploadFace(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                     @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file) {
        String faceFileSpace = faceConfig.getFaceFileSpace();
        String uploadPathPrefix = File.separator + userId;

        if (file != null) {
            FileOutputStream fileOutputStream = null;
            InputStream fileInputStream;

            String filename = file.getOriginalFilename();

            if (StringUtils.isNotBlank(filename)) {
                String[] filenameArray = filename.split("\\.");
                String suffix = filenameArray[filenameArray.length - 1];
                String newFilename = "face-" + userId + "." + suffix;

                String finalFilePath = faceFileSpace + uploadPathPrefix + File.separator + newFilename;
                File f = new File(finalFilePath);
                if (f.getParentFile() != null || !f.getParentFile().isDirectory()) {
                    f.getParentFile().mkdirs();
                }

                uploadPathPrefix += (File.separator + newFilename);

                try {
                    fileOutputStream = new FileOutputStream(f);
                    fileInputStream = file.getInputStream();
                    IOUtils.copy(fileInputStream, fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        String newUserFaceUrl = faceConfig.getUrl() + uploadPathPrefix;
        newUserFaceUrl += ("?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN));

        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(newUserFaceUrl);

        user = userService.updateUserInfo(user);
        user.setPassword(null);

        return NEXTJSONResult.ok(conventUsersVO(user));
    }

    @RequestMapping(value = "/modifyUserinfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改昵称", httpMethod = "POST")
    public NEXTJSONResult modifyUserInfo(@RequestBody ModifiedUserBO modifiedUserBO) {
        String userId = modifiedUserBO.getUserId();
        if (StringUtils.isBlank(userId)) {
            return NEXTJSONResult.errorMsg("用户id不能为空");
        }

        String nickname = modifiedUserBO.getNickname();
        String birthday = modifiedUserBO.getBirthday();
        Integer sex = modifiedUserBO.getSex();
        if (StringUtils.isBlank(birthday) && StringUtils.isBlank(nickname) && sex == null) {
            return NEXTJSONResult.errorMsg("修改用户信息不能为空");
        }

        if (sex != null && sex != 0 && sex != 1) {
            return NEXTJSONResult.errorMsg("性别错误");
        }

        if (StringUtils.isNotBlank(nickname) && nickname.length() >= 12) {
            return NEXTJSONResult.errorMsg("昵称长度过长");
        }

        if (StringUtils.isNotBlank(birthday)) {
            if (!DateUtil.isValidDate(birthday,DateUtil.ISO_EXPANDED_DATE_FORMAT)){
                return NEXTJSONResult.errorMsg("日期格式不正确");
            }
        }

        Users user = new Users();
        user.setId(userId);
        user.setSex(sex);
        user.setNickname(nickname);
        user.setBirthday(birthday);
        user = userService.updateUserInfo(user);
        user.setPassword(null);
        return NEXTJSONResult.ok(conventUsersVO(user));
    }

    private UserVO conventUsersVO(Users users) {
        String token = redisOperator.get(REDIS_USER_TOKEN + ":" + users.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(users, userVO);
        userVO.setToken(token);
        return userVO;
    }
}
