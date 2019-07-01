package com.design.oa.dao;

import com.design.oa.model.LeaveNote;

import java.util.List;

public interface LeaveNoteMapper {
    int deleteByPrimaryKey(Integer leaveNoteId);

    int insert(LeaveNote record);

    int insertSelective(LeaveNote record);

    LeaveNote selectByPrimaryKey(Integer leaveNoteId);

    int updateByPrimaryKeySelective(LeaveNote record);

    int updateByPrimaryKey(LeaveNote record);

    List<LeaveNote> selectByUserId(int userId);

    LeaveNote selectLeaveNote(int parseInt);
}