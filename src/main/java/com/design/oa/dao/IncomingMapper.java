package com.design.oa.dao;

import com.design.oa.model.Incoming;

import java.util.List;

public interface IncomingMapper {
    int deleteByPrimaryKey(Integer incomingId);

    int insert(Incoming record);

    int insertSelective(Incoming record);

    Incoming selectByPrimaryKey(Integer incomingId);

    int updateByPrimaryKeySelective(Incoming record);

    int updateByPrimaryKey(Incoming record);

    void updateStateById(Incoming incoming);

    List<Incoming> selectByUserId(Integer userId);

    Incoming selectByTitleAndUrgAndCreater(String title, int outgoingMessageUrgent, int userId, int businessKey);
}