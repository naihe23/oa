package com.design.oa.service;

import com.design.oa.model.LeaveNote;

import java.util.List;

public interface LeaveNoteService {
    int leaveForm(LeaveNote leaveNote);

    List<LeaveNote> getLeaveRecord(int userId);

    int leaveCancel(int leaveNoteId);

    LeaveNote selectByPrimaryKey(int parseInt);

    int updateLeaveNote(LeaveNote leaveNote);
}
