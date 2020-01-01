package com.next.service;

import com.next.pojo.Staff;
import com.next.pojo.vo.StaffVo;

import java.util.List;

public interface StaffService {

    /**
     * 查询演职员列表
     * @param trailer
     * @param relo
     * @return
     */
    List<StaffVo> queryStaffs(String trailer,Integer relo);
}
