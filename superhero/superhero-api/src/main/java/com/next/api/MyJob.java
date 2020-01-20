package com.next.api;

/**
 * 定时任务处理
 */

import com.next.pojo.Movie;
import com.next.pojo.bo.MovieMQMessageBo;
import com.next.redis.RedisOperator;
import com.next.service.MovieService;
import com.next.utils.DateUtil;
import com.next.utils.JsonUtils;
import com.next.utils.MoviePushUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MyJob {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void test(){
        System.out.println(DateUtil.dateToString(new Date(),DateUtil.DATE_PATTERN));

        Date tomorrow = DateUtil.dateIncreaseByDay(new Date(), 1);
        String tomorrowStr = DateUtil.dateToString(tomorrow, DateUtil.ISO_EXPANDED_DATE_FORMAT);
        String releaseDateStrKey = "MOVIE:" + tomorrowStr;

        String s = redisOperator.get(releaseDateStrKey);

        if (StringUtils.isNotEmpty(s)){
            List<Movie> movieList = JsonUtils.jsonToList(s, Movie.class);

            String movieNames = "";

            if (movieList != null && movieList.size()>0){
                for (Movie m :
                        movieList) {
                    movieNames += ("《"+m.getName() +"》,");
                }
            }

            String title = "新片上映";
            String text = movieNames + "将于明日上映";

            MovieMQMessageBo movieMQMessageBo = new MovieMQMessageBo();
            movieMQMessageBo.setTitle(title);
            movieMQMessageBo.setText(text);

            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_PUSH,"push.new.movie.display",
                    JsonUtils.objectToJson(movieMQMessageBo));

        }
    }
}
