package com.next.api.controller;


import com.next.pojo.NEXTObjectMap;
import com.next.service.CarouselService;
import com.next.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/index")
@Api(value = "首页", tags = {"首页相关接口"})
public class IndexController extends BaseController{

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/carousel/list", method = RequestMethod.POST)
    @ApiOperation(value = "首页轮播图", httpMethod = "POST")
    public NEXTObjectMap list() {
        return NEXTObjectMap.ok(carouselService.queryAll());
    }

    @RequestMapping(value = "/movie/hot", method = RequestMethod.POST)
    @ApiOperation(value = "热门超英预告", httpMethod = "POST")
    public NEXTObjectMap hot(@ApiParam(required = true,name = "type", value = "超英预告") @RequestParam String type) {
        if (StringUtils.isBlank(type)) {
            return new NEXTObjectMap(400, "error", null);
        }
        return NEXTObjectMap.ok(movieService.queryHotSuperhero(type));
    }

    @RequestMapping(value = "/guessULike", method = RequestMethod.POST)
    @ApiOperation(value = "猜你喜欢", httpMethod = "POST")
    public NEXTObjectMap guessULike() {
        Integer counts = movieService.queryAllTrailerCounts();
        Integer[] guessIndexArray = getGuessULikeArray(counts);

        return NEXTObjectMap.ok(guessIndexArray);
    }



}
