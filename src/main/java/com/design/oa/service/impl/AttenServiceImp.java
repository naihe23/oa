package com.design.oa.service.impl;

import com.design.oa.dao.AttendanceAdminMapper;
import com.design.oa.dao.AttendanceRecordMapper;
import com.design.oa.model.AttendanceRecord;
import com.design.oa.model.LeaveNote;
import com.design.oa.service.AttenService;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service(value = "attenServiceImp")
public class AttenServiceImp implements AttenService {

    @Resource
    AttendanceRecordMapper attendanceRecordMapper;
    @Resource
    AttendanceAdminMapper attendanceAdminMapper;

    @Override
    public int initialWorkTime(AttendanceRecord attendanceRecord, String address) {
        int status = 0;
        int state = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String address1 = attendanceAdminMapper.selectAddress();
            if (address.equals(address1)) {
                Time workTime = attendanceAdminMapper.selectWorkTime();
                Date signInTime = attendanceRecord.getAttWorkTime();
                String date1 = df.format(signInTime);
                Date date2 = df.parse(date1);
                Time time = new Time(date2.getTime());
                if (time.after(workTime)) {
                    attendanceRecord.setAttState("迟到");
                    status = 401;
                }
                state = attendanceRecordMapper.insertSelective(attendanceRecord);
            } else
                return 403;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (state > 0 && status == 401)
            return 402;
        if (state > 0 && status == 0)
            return 201;
        return 401;
    }

    @Override
    public AttendanceRecord getWorkTime(int userId) {
        AttendanceRecord attendanceRecord = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String attDate = null;
        try {
            attDate = sdf.format(date.getTime());
            attendanceRecord = attendanceRecordMapper.selectByUserAndDate(userId, attDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendanceRecord;
    }

    @Override
    public int initialOffTime(AttendanceRecord attendanceRecord, String address) {
        int status = 0;
        int state = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String address1 = attendanceAdminMapper.selectAddress();
            if (address.equals(address1)) {
                Time offTime = attendanceAdminMapper.selectOffTime();
                Date signInTime = attendanceRecord.getAttOffTime();
                String date1 = df.format(signInTime);
                Date date2 = df.parse(date1);
                Time time = new Time(date2.getTime());
                if (time.before(offTime)) {
                    attendanceRecord.setAttState("早退");
                    status = 401;
                }else
                    attendanceRecord.setAttState("正常");
                state = attendanceRecordMapper.updateByUserAndDate(attendanceRecord);
            } else
                return 403;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (state > 0 && status == 401)
            return 402;
        if (state > 0 && status == 0)
            return 201;
        return 401;
    }

    @Override
    public List<AttendanceRecord> selectRecordsByUser(Integer userId) {
        List <AttendanceRecord> recordList = attendanceRecordMapper.selectRecordsByUser(userId);
        return recordList;
    }

    @Override
    public int repairAtten(int userId, String attDate, String attRepairReason, String attRepairType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date attDate1 = sdf.parse(attDate);
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setUserId(userId);
            attendanceRecord.setAttDate(attDate1);
            attendanceRecord.setAttRepairReason(attRepairReason);
            attendanceRecord.setAttRepairType(attRepairType);
            attendanceRecord.setAttState("正常");
            int state = attendanceRecordMapper.updateByUserAndDate(attendanceRecord);
            if(state>0){
                return 201;
            }else{
                attendanceRecordMapper.insertSelective(attendanceRecord);
                return 201;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 401;
    }

    @Override
    public HashMap<String, Integer> getRecordMessage(Integer userId,String curDate) {
        try {
            int late = attendanceRecordMapper.getLateDays(userId, curDate);
            int leaveEarly = attendanceRecordMapper.getLeaveEarlyDays(userId, curDate);
            int leave = attendanceRecordMapper.getLeaveDays(userId, curDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String curDate1 = dateFormat.format(date);
            int totalDays = 0;
            if (curDate.equals(curDate1)) {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd");
                Date date1 = new Date();
                if( Integer.parseInt(dateFormat1.format(date1))>26){
                    totalDays = Integer.parseInt(dateFormat1.format(date1))-1-8;
                }else if( Integer.parseInt(dateFormat1.format(date1))>19){
                    totalDays = Integer.parseInt(dateFormat1.format(date1))-1-6;
                }else if( Integer.parseInt(dateFormat1.format(date1))>12){
                    totalDays = Integer.parseInt(dateFormat1.format(date1))-1-4;
                }else if( Integer.parseInt(dateFormat1.format(date1))>5){
                    totalDays = Integer.parseInt(dateFormat1.format(date1))-1-2;
                }else{
                    totalDays = Integer.parseInt(dateFormat1.format(date1))-1;
                }
            } else {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = dateFormat2.parse(curDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            int leaveWork = totalDays - late - leaveEarly - leave;
            HashMap<String, Integer> map = new HashMap<>();
            map.put("late", late);
            map.put("leaveEarly", leaveEarly);
            map.put("leave", leave);
            map.put("leaveWork",leaveWork);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertSelective(AttendanceRecord attendanceRecord) {
        attendanceRecordMapper.insertSelective(attendanceRecord);
    }
    @Override
    public void insertAttenRecord(LeaveNote leaveNote){
        Date date = leaveNote.getLeaveStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leaveNote.getLeaveEndTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        while (date.before(calendar.getTime())) {
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setUserId(leaveNote.getUserId());
            attendanceRecord.setAttDate(date);
            attendanceRecord.setAttState("请假");
            insertSelective(attendanceRecord);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar1.getTime();
        }
    }


}
