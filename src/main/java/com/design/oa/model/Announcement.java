package com.design.oa.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Announcement implements Serializable {
    private static final long serialVersionUID = -9078480328391999315L;
    private Integer annId;

    private Integer userId;

    private String annTitle;

    private Integer annType;

    private String annState;

    private Date annStartTime;

    private Date annEndTime;

    private String annRange;

    private String annText;

    private String annCreater;

    private String userDepartment;

    private String annTypeName;

    private int[] department;

    private int[] user;

    private int[] role;

    private String deploymentName;

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public int[] getDepartment() {
        return department;
    }

    public void setDepartment(int[] department) {
        this.department = department;
    }

    public int[] getUser() {
        return user;
    }

    public void setUser(int[] user) {
        this.user = user;
    }

    public int[] getRole() {
        return role;
    }

    public void setRole(int[] role) {
        this.role = role;
    }

    public Integer getAnnId() {
        return annId;
    }

    public void setAnnId(Integer annId) {
        this.annId = annId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAnnTitle() {
        return annTitle;
    }

    public void setAnnTitle(String annTitle) {
        this.annTitle = annTitle == null ? null : annTitle.trim();
    }

    public Integer getAnnType() {
        return annType;
    }

    public void setAnnType(Integer annType) {
        this.annType = annType;
    }

    public String getAnnState() {
        return annState;
    }

    public void setAnnState(String annState) {
        this.annState = annState == null ? null : annState.trim();
    }

    public Date getAnnStartTime() {
        return annStartTime;
    }

    public void setAnnStartTime(Date annStartTime) {
        this.annStartTime = annStartTime;
    }

    public Date getAnnEndTime() {
        return annEndTime;
    }

    public void setAnnEndTime(Date annEndTime) {
        this.annEndTime = annEndTime;
    }

    public String getAnnRange() {
        return annRange;
    }

    public void setAnnRange(String annRange) {
        this.annRange = annRange == null ? null : annRange.trim();
    }

    public String getAnnText() {
        return annText;
    }

    public void setAnnText(String annText) {
        this.annText = annText == null ? null : annText.trim();
    }

    public String getAnnCreater() {
        return annCreater;
    }

    public void setAnnCreater(String annCreater) {
        this.annCreater = annCreater;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getAnnTypeName() {
        return annTypeName;
    }

    public void setAnnTypeName(String annTypeName) {
        this.annTypeName = annTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Announcement announcement = (Announcement) o;

        return annId != null ? annId.equals(announcement.annId) : announcement.annId == null;
    }

    @Override
    public int hashCode() {
        return annId != null ? annId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "annId=" + annId +
                ", userId=" + userId +
                ", annTitle='" + annTitle + '\'' +
                ", annType=" + annType +
                ", annState='" + annState + '\'' +
                ", annStartTime=" + annStartTime +
                ", annEndTime=" + annEndTime +
                ", annRange='" + annRange + '\'' +
                ", annText='" + annText + '\'' +
                ", department=" + Arrays.toString(department) +
                ", user=" + Arrays.toString(user) +
                ", role=" + Arrays.toString(role) +
                '}';
    }
}