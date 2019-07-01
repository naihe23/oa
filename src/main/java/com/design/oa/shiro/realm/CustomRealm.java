package com.design.oa.shiro.realm;


import com.design.oa.model.Admin;
import com.design.oa.model.Menu;
import com.design.oa.model.Role;
import com.design.oa.model.User;
import com.design.oa.service.AdminService;
import com.design.oa.service.RoleService;
import com.design.oa.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Resource
    AdminService adminService;

    @Resource
    RoleService roleService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipals().getPrimaryPrincipal();
        List<Role> roles = userService.getRoles(user.getUserId());
        System.out.println("roleSize:"+roles.size());
        for (Role role:roles) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            List<Menu> menus = roleService.getMenus(role.getRoleId());
            System.out.println("menuSize:"+menus.size());
            for (Menu menu:menus){
                simpleAuthorizationInfo.addStringPermission(menu.getMenuUrl());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userName = (String) authenticationToken.getPrincipal();
        User user = userService.findUserByUserName(userName);
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (user == null) {
            Admin admin = adminService.findAdminByAdminName(userName);
            if (admin == null) {
                return null;
            } else {
                System.out.println("admin");
                simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin,
                        admin.getAdminPassword(), ByteSource.Util.bytes(admin.getAdminSalt()), this.getName());
            }
        } else {
            int status = user.getUserIslocked();
            if(status==2){
                throw new LockedAccountException();
            }
            System.out.println("user");
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
                    user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()), this.getName());
        }
        return simpleAuthenticationInfo;
    }

    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
