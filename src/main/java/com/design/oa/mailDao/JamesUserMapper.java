package com.design.oa.mailDao;

import com.design.oa.mailModel.JamesUser;

public interface JamesUserMapper {
    int deleteByPrimaryKey(String userName);

    int insert(JamesUser record);

    int insertSelective(JamesUser record);

    JamesUser selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(JamesUser record);

    int updateByPrimaryKey(JamesUser record);
}