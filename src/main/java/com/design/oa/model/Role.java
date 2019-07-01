package com.design.oa.model;

import java.sql.Timestamp;
import java.util.Date;

public class Role {
    private Integer roleId;

    private String roleName;

    private String roleDescribe;

    private Timestamp roleCreateTime;

    private String roleCreater;

    private String roleUpdater;

    private Timestamp roleUpdateTime;

    private Integer roleState;

    private String menuName;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe == null ? null : roleDescribe.trim();
    }

    public Timestamp getRoleCreateTime() {

        return roleCreateTime;
    }

    public void setRoleCreateTime(Timestamp roleCreateTime) {
        this.roleCreateTime = roleCreateTime;
    }

    public String getRoleCreater() {
        return roleCreater;
    }

    public void setRoleCreater(String roleCreater) {
        this.roleCreater = roleCreater == null ? null : roleCreater.trim();
    }

    public String getRoleUpdater() {
        return roleUpdater;
    }

    public void setRoleUpdater(String roleUpdater) {
        this.roleUpdater = roleUpdater == null ? null : roleUpdater.trim();
    }

    public Date getRoleUpdateTime() {
        return roleUpdateTime;
    }

    public void setRoleUpdateTime(Timestamp roleUpdateTime) {
        this.roleUpdateTime = roleUpdateTime;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }
}