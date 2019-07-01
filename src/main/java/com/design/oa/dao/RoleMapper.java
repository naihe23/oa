package com.design.oa.dao;

import com.design.oa.model.Menu;
import com.design.oa.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAllRolesOrByRoleName(@Param("roleName") String roleName);

    Role selectByRoleName(@Param("roleName") String roleName);


    List<Role> getUserRolesMessage(int userId);

    List<Role> seleceAllRoles();
}