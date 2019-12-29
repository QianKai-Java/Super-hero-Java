package com.next.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class TestController {

    @GetMapping("test")
    public String test(){
        return "hello world";
    }
}
