package com.next.api.controller;


import com.next.pojo.BaseResultMap;
import com.next.pojo.Movie;
import com.next.pojo.NEXTJSONResult;
import com.next.pojo.NEXTObjectMap;
import com.next.service.CarouselService;
import com.next.service.MovieService;
import com.next.service.StaffService;
import com.next.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
@Api(value = "搜索", tags = {"搜索页相关接口"})
public class SearchController extends BaseController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private StaffService staffService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "搜索电影列表", httpMethod = "POST")
    public NEXTObjectMap guessULike(@ApiParam(name = "keywords", value = "查询的关键字", required = false) @RequestParam String keywords,
                                    @ApiParam(name = "page", value = "查询页数", required = false) @RequestParam Integer page,
                                    @ApiParam(name = "pageSize", value = "查询每一页记录", required = false) @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            keywords = "";

        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 9;
        }

        return NEXTObjectMap.ok(movieService.searchTrailer(keywords, page, pageSize));
    }

    @RequestMapping(value = "/trailer/{trailerId}", method = RequestMethod.POST)
    @ApiOperation(value = "预告片详情", httpMethod = "POST")
    public NEXTJSONResult trailer(@ApiParam(name = "trailerId", value = "预告主键", required = true)@PathVariable String trailerId){
        if (StringUtils.isBlank(trailerId)){
            return NEXTJSONResult.errorMsg("");
        }

        return NEXTJSONResult.ok(movieService.queryTrailerInfo(trailerId));
    }

    @RequestMapping(value = "/staff/{trailerId}/{role}", method = RequestMethod.POST)
    @ApiOperation(value = "查询演职人员", httpMethod = "POST")
    public NEXTJSONResult staff(@ApiParam(name = "trailerId", value = "预告主键", required = true)@PathVariable String trailerId,
                                @ApiParam(name = "role", value = "演职员角色", required = true)@PathVariable Integer role){
        if (StringUtils.isBlank(trailerId)){
            return NEXTJSONResult.errorMsg("");
        }

        return NEXTJSONResult.ok(staffService.queryStaffs(trailerId,role));
    }
}
