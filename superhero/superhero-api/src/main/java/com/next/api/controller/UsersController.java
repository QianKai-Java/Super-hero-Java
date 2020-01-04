package com.next.api.controller;


import com.next.pojo.NEXTJSONResult;
import com.next.pojo.NEXTObjectMap;
import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.bo.RegistLoginUserBo;
import com.next.pojo.vo.UserVO;
import com.next.pojo.vo.WXSessionVo;
import com.next.service.MovieService;
import com.next.service.StaffService;
import com.next.service.UserService;
import com.next.utils.HttpClientUtil;
import com.next.utils.JsonUtils;
import com.next.utils.MD5Utils;
import com.next.utils.MPWXConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "用户退出登录")
    public NEXTJSONResult logout(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        return NEXTJSONResult.ok();
    }

    @RequestMapping(value = "/registOrLogin", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册和登录")
    public NEXTJSONResult registerOrLogin(@RequestBody RegistLoginUserBo registLoginUserBo) {
        if (StringUtils.isBlank(registLoginUserBo.getPassword()) || StringUtils.isBlank(registLoginUserBo.getUsername())) {
            return NEXTJSONResult.errorMsg("用户名或密码不能为空");
        }

        Users user = null;
        boolean isExist = userService.queryUsernameIsExist(registLoginUserBo.getUsername());

        if (isExist){
            try {
                user = userService.queryUserForLogin(registLoginUserBo.getUsername(),
                        MD5Utils.getMD5Str(registLoginUserBo.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            user = userService.saveUser(registLoginUserBo);

        }

        UserVO userVO = setRedisUserToken(user);
        return NEXTJSONResult.ok(userVO);
    }

}
