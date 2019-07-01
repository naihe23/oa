package com.design.oa.model;

public class UserAuthority {
    private int userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String departmentName;
    private String roleName;
    private int userIsLocked;

    public int getUserIsLocked() {
        return userIsLocked;
    }

    public void setUserIsLocked(int userIsLocked) {
        this.userIsLocked = userIsLocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
