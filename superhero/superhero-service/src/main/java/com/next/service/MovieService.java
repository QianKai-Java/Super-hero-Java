package com.next.service;

import com.next.pojo.Movie;

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
}
