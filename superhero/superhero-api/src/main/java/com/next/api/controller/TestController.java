package com.next.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class TestController extends BaseController{

    @GetMapping("test")
    public String test(){
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
