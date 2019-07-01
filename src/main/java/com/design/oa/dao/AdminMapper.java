package com.design.oa.dao;

import com.design.oa.model.Admin;
import com.design.oa.model.User;

import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin findAdminByAdminName(String userName);

}