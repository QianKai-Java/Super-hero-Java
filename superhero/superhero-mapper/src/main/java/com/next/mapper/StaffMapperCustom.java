package com.next.mapper;

import com.next.my.mapper.MyMapper;
import com.next.pojo.Staff;
import com.next.pojo.vo.StaffVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffMapperCustom extends MyMapper<Staff> {

    /**
     * 根据预告片ID和角色查询导演列表和演员列表
     *
     * @param trailerId
     * @param role
     * @return
     */
    List<StaffVo> queryStaffs(
            @Param(value = "trailerId") String trailerId,
            @Param(value = "role") Integer role);
}