package com.design.oa.service.impl;

import com.design.oa.dao.*;
import com.design.oa.model.Admin;
import com.design.oa.model.Menu;
import com.design.oa.model.Role;
import com.design.oa.service.AdminService;
import com.design.oa.utils.EncryPassword;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImp implements AdminService {

    @Resource
    AdminMapper adminMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public int register(Admin admin) {
        Admin admin1 = EncryPassword.encryptedAdminPassword(admin);
        return adminMapper.insertSelective(admin1);
    }

    @Override
    public Admin findAdminByAdminName(String userName) {

        return adminMapper.findAdminByAdminName(userName);
    }

    @Override
    public List<Menu> searchUserTreeById(int menuId) {
        List<Menu> menuList = menuMapper.searchUserTreeById(menuId);
        if (menuList != null && menuList.size() != 0) {
            for (Menu menu : menuList) {
                List<Menu> menuList1 = searchUserTreeById(menu.getMenuId());
                menu.setChildren(menuList1);
            }
        }
        return menuList;
    }

    @Override
    public int addRole(String roleName, String roleDescribe, int[] roleMenu) {
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRoleCreater(admin.getAdminName());
        role.setRoleCreateTime(Timestamp.valueOf(df.format(new Date())));
        Role role1 = roleMapper.selectByRoleName(roleName);
        if (role1 != null) {
            return 402;
        }
        int isIns = roleMapper.insertSelective(role);
        int isInsert = roleMenuMapper.insert(role.getRoleId(), roleMenu);
        if (isIns > 0 && isInsert > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(int roleId, String roleName, String roleDescribe, int[] roleMenu) {
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleState(1);
        role.setRoleName(roleName);
        role.setRoleDescribe(roleDescribe);
        role.setRoleUpdater(admin.getAdminName());
        role.setRoleUpdateTime(Timestamp.valueOf(df.format(new Date())));
        int isUpdate = roleMapper.updateByPrimaryKey(role);
        int isDelete = roleMenuMapper.deleteByRoleId(roleId);
        int isIns = roleMenuMapper.insert(roleId, roleMenu);
        if (isUpdate > 0 && isDelete > 0 && isIns > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(int roleId) {
        String roleName =  roleMapper.selectByPrimaryKey(roleId).getRoleName();
        if(roleName.equals("系统管理员")){
            return 403;
        }
        int isHaveUser  = userRoleMapper.selectByPrimaryKey(roleId);
        if(isHaveUser>0){
            return 402;
        }
        int isDelete = roleMapper.deleteByPrimaryKey(roleId);
         ;
        int isDeleteMenu = roleMenuMapper.deleteByRoleId(roleId);
        System.out.println("isd" + isDelete);
        System.out.println("isdm" + isDeleteMenu);
        if (isDelete > 0 && isDeleteMenu > 0 ) {
            return 201;
        } else
            return 401;
    }

    @Override
    public int roleLocked(int roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleState(2);
        int isLocked = roleMapper.updateByPrimaryKeySelective(role);
        if (isLocked > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    public int roleUnLocked(int roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleState(1);
        int isLocked = roleMapper.updateByPrimaryKeySelective(role);
        if (isLocked > 0) {
            return 201;
        } else
            return 401;
    }


}
