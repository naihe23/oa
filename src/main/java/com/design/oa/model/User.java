package com.design.oa.model;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private Integer userId;

    private Integer departmentId;

    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private Integer adminId;

    private String userName;

    private String userSex;

    private String userPhone;

    private String userAddress;

    private String userEmail;

    private String userQq;

    private String userJob;

    private String userPassword;

    private Integer userIslocked;

    private Date userCreatetime;

    private Date userBirthday;

    private String userCompanyPhone;

    private String userCompanyFex;

    private String userHomeZip;

    private String userHomePhone;

    private String userSalt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
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
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob == null ? null : userJob.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getUserIslocked() {
        return userIslocked;
    }

    public void setUserIslocked(Integer userIslocked) {
        this.userIslocked = userIslocked;
    }

    public Date getUserCreatetime() {
        return userCreatetime;
    }

    public void setUserCreatetime(Date userCreatetime) {
        this.userCreatetime = userCreatetime;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserCompanyPhone() {
        return userCompanyPhone;
    }

    public void setUserCompanyPhone(String userCompanyPhone) {
        this.userCompanyPhone = userCompanyPhone == null ? null : userCompanyPhone.trim();
    }

    public String getUserCompanyFex() {
        return userCompanyFex;
    }

    public void setUserCompanyFex(String userCompanyFex) {
        this.userCompanyFex = userCompanyFex == null ? null : userCompanyFex.trim();
    }

    public String getUserHomeZip() {
        return userHomeZip;
    }

    public void setUserHomeZip(String userHomeZip) {
        this.userHomeZip = userHomeZip == null ? null : userHomeZip.trim();
    }

    public String getUserHomePhone() {
        return userHomePhone;
    }

    public void setUserHomePhone(String userHomePhone) {
        this.userHomePhone = userHomePhone == null ? null : userHomePhone.trim();
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt == null ? null : userSalt.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", adminId=" + adminId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userPhone=" + userPhone +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userQq=" + userQq +
                ", userJob='" + userJob + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userIslocked=" + userIslocked +
                ", userCreatetime=" + userCreatetime +
                ", userBirthday=" + userBirthday +
                ", userCompanyPhone='" + userCompanyPhone + '\'' +
                ", userCompanyFex='" + userCompanyFex + '\'' +
                ", userHomeZip='" + userHomeZip + '\'' +
                ", userHomePhone='" + userHomePhone + '\'' +
                ", userSalt='" + userSalt + '\'' +
                '}';
    }
}