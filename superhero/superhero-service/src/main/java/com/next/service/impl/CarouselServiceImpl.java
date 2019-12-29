package com.next.service.impl;

import com.next.mapper.CarouselMapper;
import com.next.pojo.Carousel;
import com.next.pojo.NEXTObjectMap;
import com.next.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll() {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        example.createCriteria().andEqualTo("isShow",1);
        List<Carousel> carouselList = carouselMapper.selectByExample(example);
        return carouselList;
    }
}
