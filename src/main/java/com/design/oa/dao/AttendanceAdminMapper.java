package com.design.oa.dao;

import com.design.oa.model.AttendanceAdmin;

import java.sql.Time;

public interface AttendanceAdminMapper {
    int deleteByPrimaryKey(Integer attAdminId);

    int insert(AttendanceAdmin record);

    int insertSelective(AttendanceAdmin record);

    AttendanceAdmin selectByPrimaryKey(Integer attAdminId);

    int updateByPrimaryKeySelective(AttendanceAdmin record);

    int updateSelective(AttendanceAdmin record);

    int updateByPrimaryKey(AttendanceAdmin record);

    Time selectWorkTime();

    Time selectOffTime();

    String selectAddress();
}