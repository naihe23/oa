package com.design.oa.dao;

import com.design.oa.model.EmialAttachment;

public interface EmialAttachmentMapper {
    int deleteByPrimaryKey(Integer emailAttachmentId);

    int insert(EmialAttachment record);

    int insertSelective(EmialAttachment record);

    EmialAttachment selectByPrimaryKey(Integer emailAttachmentId);

    int updateByPrimaryKeySelective(EmialAttachment record);

    int updateByPrimaryKey(EmialAttachment record);
}