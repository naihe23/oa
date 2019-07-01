package com.design.oa.service.impl;

import com.design.oa.dao.AttendanceAdminMapper;
import com.design.oa.model.AttendanceAdmin;
import com.design.oa.service.AttenAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttenAdminServiceImp implements AttenAdminService {

    @Resource
    AttendanceAdminMapper attendanceAdminMapper;

    @Override
    public AttendanceAdmin getAttenSetting() {
        AttendanceAdmin attendanceAdmin = attendanceAdminMapper.selectByPrimaryKey(1);
        return attendanceAdmin;
    }

    @Override
    public int editAttenAdmin(AttendanceAdmin attendanceAdmin) {
        attendanceAdmin.setAdminId(2);
        int state = attendanceAdminMapper.updateSelective(attendanceAdmin);
        if (state > 0)
            return 201;
        else
            return 401;
    }
}
