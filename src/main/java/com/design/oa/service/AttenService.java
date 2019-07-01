package com.design.oa.service;

import com.design.oa.model.AttendanceRecord;
import com.design.oa.model.LeaveNote;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface AttenService extends Serializable {
    int initialWorkTime(AttendanceRecord attendanceRecord, String address);

    AttendanceRecord getWorkTime(int userId);

    int initialOffTime(AttendanceRecord attendanceRecord,String address);

    List<AttendanceRecord> selectRecordsByUser(Integer userId);

    int repairAtten(int userId, String attDate, String attRepairReason, String attRepairType);

    HashMap<String, Integer> getRecordMessage(Integer userId, String curDate);

    void insertSelective(AttendanceRecord attendanceRecord);
    void insertAttenRecord(LeaveNote leaveNote);

}
