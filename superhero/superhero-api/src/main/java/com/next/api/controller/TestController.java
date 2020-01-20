package com.next.api.controller;

import com.next.api.RabbitMQConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

@RestController
@ApiIgnore
public class TestController extends BaseController{

    @GetMapping("test")
    public String test(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_PUSH,"push.orders.created","订单被创建了-" + new Date());
        return "hello world";
    }

    @GetMapping("/redis/get")
    public Object get(){
        return redisOperator.get("key");
    }

    @GetMapping("/redis/set")
    public String set(){
        redisOperator.set("key","hello word");
        return "success";
    }
}
