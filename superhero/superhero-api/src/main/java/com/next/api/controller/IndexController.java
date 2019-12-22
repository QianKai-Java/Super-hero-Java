package com.next.api.controller;


import com.next.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/index/carousel/list")
    public Object list(){
        return carouselService.queryAll();
    }

}
