package com.next.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.next.common.MovieTypeEnum;
import com.next.mapper.MovieMapper;
import com.next.pojo.Movie;
import com.next.service.MovieService;
import com.next.utils.JqGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(propagation = Propagation.SUPPORTS)
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryAllTrailerCounts() {
        int count = movieMapper.selectCount(new Movie());
        return count;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Movie> queryAllTrailers() {
        return movieMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JqGridResult searchTrailer(String keywords, Integer page, Integer pageSize) {
        Example example = new Example(Movie.class);

        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.orLike("name","%"+keywords+"%");
        criteria.orLike("originalName","%"+keywords+"%");

        PageHelper.startPage(page,pageSize);

        List<Movie> movieList = movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfoList = new PageInfo<>(movieList);

        JqGridResult gridResult = new JqGridResult();
        gridResult.setPage(page);
        gridResult.setRows(movieList);
        gridResult.setTotal(pageInfoList.getPages());
        gridResult.setRecords(gridResult.getTotal());

        return gridResult;
    }

    @Override
    public Movie queryTrailerInfo(String trailerId) {
        return movieMapper.selectByPrimaryKey(trailerId);
    }
}
