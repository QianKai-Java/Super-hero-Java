package com.next.api.controller;


import com.next.pojo.NEXTJSONResult;
import com.next.pojo.NEXTObjectMap;
import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.vo.UserVO;
import com.next.pojo.vo.WXSessionVo;
import com.next.service.MovieService;
import com.next.service.StaffService;
import com.next.service.UserService;
import com.next.utils.HttpClientUtil;
import com.next.utils.JsonUtils;
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
@Api(value = "微信小程序授权登录", tags = {"微信小程序授权登录"})
public class MPWXController extends BaseController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/mpWXLogin/{code}", method = RequestMethod.POST)
    @ApiOperation(value = "微信登录", httpMethod = "POST")
    public NEXTObjectMap guessULike(@ApiParam(name = "code", value = "授权凭证", required = true) @PathVariable String code,
                                    @RequestBody MPWXUserBO mpwxUserBO) {
        String appId = MPWXConfig.APPID;
        String secret = MPWXConfig.SECRET;
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", secret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String result = HttpClientUtil.doGet(url, map);
        WXSessionVo wxSessionVo = JsonUtils.jsonToPojo(result, WXSessionVo.class);
        String openid = wxSessionVo.getOpenid();

        Users users = userService.queryUserForLoginByMPWX(openid);

        //判断用户是否存在，不存在则注册
        if (users == null) {
            users = userService.saveUserMPWX(openid, mpwxUserBO);
        }

        String userId = users.getId();
        String token = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userId, token);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(users,userVO);
        userVO.setToken(token);

        return NEXTObjectMap.ok(userVO);
    }

}
