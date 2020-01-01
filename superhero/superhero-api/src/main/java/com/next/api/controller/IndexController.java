package com.next.api.controller;


import com.next.pojo.Movie;
import com.next.pojo.NEXTJSONResult;
import com.next.pojo.NEXTObjectMap;
import com.next.service.CarouselService;
import com.next.service.MovieService;
import com.next.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public NEXTJSONResult hot(@ApiParam(required = true,name = "type", value = "超英预告") @RequestParam String type) {
        if (StringUtils.isBlank(type)) {
            return new NEXTJSONResult(400, "error", null);
        }
        return NEXTJSONResult.ok(movieService.queryHotSuperhero(type));
    }

    @RequestMapping(value = "/guessULike", method = RequestMethod.POST)
    @ApiOperation(value = "猜你喜欢", httpMethod = "POST")
    public NEXTJSONResult guessULike() {
        Integer counts = movieService.queryAllTrailerCounts();

        Integer[] guessIndexArray = getGuessULikeArray(counts);

        List<Movie> movieList = new ArrayList<>();
        for (Integer i :
                guessIndexArray) {
            String s = redisOperator.get("guess-trailer-id:" + i);
            Movie movie = JsonUtils.jsonToPojo(s,Movie.class);
            movieList.add(movie);
        }
        return NEXTJSONResult.ok(movieList);
    }



}
