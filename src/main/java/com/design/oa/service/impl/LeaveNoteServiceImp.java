package com.design.oa.service.impl;

import com.design.oa.dao.AttendanceRecordMapper;
import com.design.oa.dao.LeaveNoteMapper;
import com.design.oa.model.AttendanceRecord;
import com.design.oa.model.LeaveNote;
import com.design.oa.service.LeaveNoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LeaveNoteServiceImp implements LeaveNoteService {

    @Resource
    LeaveNoteMapper leaveNoteMapper;
    @Resource
    AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public int leaveForm(LeaveNote leaveNote) {
        int state = leaveNoteMapper.insertSelective(leaveNote);

        if (state > 0)
            return 201;
        return 401;
    }

    @Override
    public List<LeaveNote> getLeaveRecord(int userId) {
        List<LeaveNote> leaveNoteList = leaveNoteMapper.selectByUserId(userId);
        return leaveNoteList;
    }

    @Override
    public int leaveCancel(int leaveNoteId) {
        LeaveNote leaveNote = new LeaveNote();
        leaveNote.setLeaveNoteId(leaveNoteId);
        leaveNote.setLeaveCancleTime(new Date());
        leaveNote.setLeaveCancleSign(1);
        int state = leaveNoteMapper.updateByPrimaryKeySelective(leaveNote);
        if (state > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    public LeaveNote selectByPrimaryKey(int parseInt) {
        LeaveNote leaveNote = leaveNoteMapper.selectLeaveNote(parseInt);
        return leaveNote;
    }

    @Override
    public int updateLeaveNote(LeaveNote leaveNote) {
       int state = leaveNoteMapper.updateByPrimaryKeySelective(leaveNote);
       if(state>0){
           return 201;
       }else
           return 401;
    }
}

