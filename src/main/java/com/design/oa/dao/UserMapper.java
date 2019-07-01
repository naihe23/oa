package com.design.oa.dao;

import com.design.oa.model.Menu;
import com.design.oa.model.Role;
import com.design.oa.model.User;
import com.design.oa.model.UserAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserByUserName(String userName);

    List<User> getAllUsers(@Param("userName") String userName);

    User selectByUserName(String userName);

    List<Role> getUserRoles(Integer userId);

    List<UserAuthority> getAllUser(@Param("userName") String userName);

    String getAllUserRole(int userId);

    String getAllUserDepar(int userId);


    List<User> selectAllUsers();

    List<User> selectByDepartmentId(int id);
}