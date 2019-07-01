package com.design.oa.dao;

import com.design.oa.model.Menu;
import com.design.oa.model.RoleMenuKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(RoleMenuKey key);

    int insert(@Param("roleId") int roleId, @Param("roleMenu") int[] roleMenu);

    int insertSelective(RoleMenuKey record);

    List<Menu> getMenus(int roleId);

    int deleteByRoleId(int roleId);
}