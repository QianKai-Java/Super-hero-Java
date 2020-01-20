package com.next.api;

/**
 * 自启动类
 */

import com.next.pojo.Movie;
import com.next.redis.RedisOperator;
import com.next.service.MovieService;
import com.next.utils.DateUtil;
import com.next.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import java.util.ArrayList;
import java.util.Date;
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
            Movie movie = movieList.get(i);

            redisOperator.set("guess-trailer-id:" + i, JsonUtils.objectToJson(movieList.get(i)));

            Date releaseDate = movie.getCreateTime();
            String releaseDateStr = DateUtil.dateToString(releaseDate, DateUtil.ISO_EXPANDED_DATE_FORMAT);
            String releaseDateStrKey = "MOVIE:" + releaseDateStr;
            String movieListStr = redisOperator.get(releaseDateStrKey);
            List<Movie> releaseList = new ArrayList<>();
            if (StringUtils.isNotEmpty(movieListStr)){
                releaseList = JsonUtils.jsonToList(movieListStr,Movie.class);
            }

            boolean isExist = false;
            for (Movie m :
                    releaseList) {
                String id = m.getId();
                if (id.equals(movie.getId())){
                    isExist = true;
                    break;
                }
            }
            if (!isExist){
                releaseList.add(movie);
                redisOperator.set(releaseDateStrKey, JsonUtils.objectToJson(releaseList));
            }

        }
    }
}
