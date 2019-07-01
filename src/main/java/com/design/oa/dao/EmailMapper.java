package com.design.oa.dao;

import com.design.oa.model.Email;

public interface EmailMapper {
    int deleteByPrimaryKey(Integer emailId);

    int insert(Email record);

    int insertSelective(Email record);

    Email selectByPrimaryKey(Integer emailId);

    int updateByPrimaryKeySelective(Email record);

    int updateByPrimaryKeyWithBLOBs(Email record);

    int updateByPrimaryKey(Email record);
}