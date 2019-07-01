package com.design.oa.service;

import com.design.oa.model.AttendanceAdmin;

public interface AttenAdminService {
    AttendanceAdmin getAttenSetting();

    int editAttenAdmin(AttendanceAdmin attendanceAdmin);
}
