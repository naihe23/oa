package com.design.oa.service;

import com.design.oa.model.Admin;
import com.design.oa.model.Menu;

import java.util.List;

public interface AdminService {

     int register(Admin admin);

    Admin findAdminByAdminName(String userName);


    List<Menu> searchUserTreeById(int menuId);

    int addRole(String roleName, String roleDescribe, int[] roleMenu);

    int updateRole(int roleId, String roleName, String roleDescribe, int[] roleMenu);

    int deleteRole(int roleId);

    int roleLocked(int roleId);

    int roleUnLocked(int roleId);
}
