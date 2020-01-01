package com.next.service.impl;

import com.next.mapper.StaffMapperCustom;
import com.next.pojo.vo.StaffVo;
import com.next.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffMapperCustom staffMapperCustom;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<StaffVo> queryStaffs(String trailer, Integer role) {
        return staffMapperCustom.queryStaffs(trailer,role);
    }
}
