package com.next.api.controller;

import com.next.pojo.Users;
import com.next.pojo.vo.UserVO;
import com.next.redis.RedisOperator;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
public class BaseController {

    @Autowired
    RedisOperator redisOperator;

    public final static String REDIS_USER_TOKEN = "redis_user_token";

    public Integer[] getGuessULikeArray(Integer counts) {

        Integer[] guessIndexArray = new Integer[5];

        for (int i = 0; i < guessIndexArray.length; i++) {

            int index = (int) (Math.random() * counts);

            if (ArrayUtils.contains(guessIndexArray, index)) {
                i--;
                continue;
            }
            guessIndexArray[i] = index;
        }

        return guessIndexArray;
    }

    public UserVO setRedisUserToken(Users users){
        String userId = users.getId();
        String token = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userId, token);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(users,userVO);
        userVO.setToken(token);

        return userVO;
    }
}
