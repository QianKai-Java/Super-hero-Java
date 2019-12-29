package com.next.service.impl;

import com.github.pagehelper.PageHelper;
import com.next.common.MovieTypeEnum;
import com.next.mapper.MovieMapper;
import com.next.pojo.Movie;
import com.next.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    /**
     * 只拿第一页数据
     *
     * @param type
     * @return
     */
    @Override
    public List<Movie> queryHotSuperhero(String type) {
        Integer page = 1;
        Integer pageSize;
        Example example = new Example(Movie.class);
        if (MovieTypeEnum.SUPERHERO.type.equals(type)) {
            example.orderBy("score").desc();
            pageSize = 10;
        } else if (MovieTypeEnum.TRAILER.type.equals(type)) {
            example.orderBy("prisedCounts").desc();
            pageSize = 2;
        } else {
            return null;
        }

        PageHelper.startPage(page, pageSize);

        List<Movie> movieList = movieMapper.selectByExample(example);

        return movieList;
    }

    @Override
    public Integer queryAllTrailerCounts() {
        int count = movieMapper.selectCount(new Movie());
        return count;
    }
}
