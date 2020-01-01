package com.next.service;

import com.next.pojo.Movie;
import com.next.utils.JqGridResult;

import java.util.List;

public interface MovieService {

    /**
     * 查询热门超级英雄预告片
     * @param type
     * @return
     */
    List<Movie> queryHotSuperhero(String type);

    /**
     * 查询电影预告表的记录数
     * @return
     */
    Integer queryAllTrailerCounts();

    /**
     * 查询所有电影记录
     * @return
     */
    List<Movie> queryAllTrailers();

    /**
     * 根据关键字查询所有的分页电影
     * @param keywords
     * @param page
     * @param pageSize
     * @return
     */
    JqGridResult searchTrailer(String keywords, Integer page, Integer pageSize);

    /**
     * 根据主键查询电影详情
     * @param trailerId
     * @return
     */
    Movie queryTrailerInfo(String trailerId);
}
