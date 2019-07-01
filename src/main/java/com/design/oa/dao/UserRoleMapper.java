package com.design.oa.dao;

import com.design.oa.model.UserRoleKey;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(@Param("userId")int userId,@Param("userRole") int[] userRole);

    int insertSelective(UserRoleKey record);

    int deleteByUserId(int userId);

    int deleteByRoleId(int roleId);

    int selectByPrimaryKey(int roleId);
}