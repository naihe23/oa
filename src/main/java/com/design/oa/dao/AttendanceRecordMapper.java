package com.design.oa.dao;

import com.design.oa.model.AttendanceRecord;

import java.util.Date;
import java.util.List;

public interface AttendanceRecordMapper {
    int deleteByPrimaryKey(Integer attRecordId);

    int insert(AttendanceRecord record);

    int insertSelective(AttendanceRecord record);

    AttendanceRecord selectByPrimaryKey(Integer attRecordId);

    int updateByPrimaryKeySelective(AttendanceRecord record);

    int updateByPrimaryKey(AttendanceRecord record);

    AttendanceRecord selectByUserAndDate(int userId, String attDate);

    int updateByUserAndDate(AttendanceRecord attendanceRecord);

    List<AttendanceRecord> selectRecordsByUser(Integer userId);

    int getLateDays(Integer userId, String curDate);

    int getLeaveEarlyDays(Integer userId, String curDate);

    int getLeaveDays(Integer userId,String curDate);
}