package com.next.api;

/**
 * 自启动类
 */

import com.next.pojo.Movie;
import com.next.redis.RedisOperator;
import com.next.service.MovieService;
import com.next.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoBootRunner implements ApplicationRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Movie> movieList = movieService.queryAllTrailers();

        for (int i = 0; i < movieList.size(); i++) {
            redisOperator.set("guess-trailer-id:" + i, JsonUtils.objectToJson(movieList.get(i)));
        }
    }
}
