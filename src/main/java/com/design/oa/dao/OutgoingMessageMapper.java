package com.design.oa.dao;

import com.design.oa.model.OutgoingMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface OutgoingMessageMapper {
    int deleteByPrimaryKey(Integer outgoingMessageId);

    int insert(OutgoingMessage record);

    int insertSelective(OutgoingMessage record);

    OutgoingMessage selectByPrimaryKey(Integer outgoingMessageId);

    int updateByPrimaryKeySelective(OutgoingMessage record);

    int updateByPrimaryKey(OutgoingMessage record);

    List<OutgoingMessage> selectByUserId(Integer userId);

    OutgoingMessage selectByTitleAndUrgAndCreater(@Param("title") String title,
                                                  @Param("outgoingMessageUrgent") int outgoingMessageUrgent,
                                                  @Param("userId") int userId,
                                                  @Param("businessKey") int businessKey);

}