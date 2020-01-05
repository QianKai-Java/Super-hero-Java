package com.next.api.controller.interceptor;

import com.next.api.config.FaceConfig;
import com.next.pojo.NEXTJSONResult;
import com.next.redis.RedisOperator;
import com.next.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    FaceConfig faceConfig;

    public final static String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 请求前拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return true(请求放行)，false(请求拦截)
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerUserId = request.getHeader("headerUserId");
        String headerUserToken = request.getHeader("headerUserToken");
        if (StringUtils.isNotBlank(headerUserId) && StringUtils.isNotBlank(headerUserToken)) {
            String token = redisOperator.get(REDIS_USER_TOKEN + ":" + headerUserId);
            if (StringUtils.isBlank(token)){
                returnErrorResponse(response,NEXTJSONResult.errorTokenMsg("token过期"));
                return false;
            }else {
                if (!token.equals(headerUserToken)){
                    returnErrorResponse(response,NEXTJSONResult.errorTokenMsg("用户在异地登录"));
                    return false;
                }
            }
        }else {
            returnErrorResponse(response,NEXTJSONResult.errorTokenMsg("请登录"));
            return false;
        }

        return true;
    }

    /**
     * 请求后，视图渲染前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求完毕后，视图渲染后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, NEXTJSONResult result){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
