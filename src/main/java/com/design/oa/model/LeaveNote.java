package com.design.oa.model;

import java.io.Serializable;
import java.util.Date;

public class LeaveNote implements Serializable{
    private Integer leaveNoteId;

    private Integer userId;

    private Date leaveStartTime;

    private Date leaveEndTime;

    private String leaveReason;

    private Date leaveCancleTime;

    private Integer leaveCancleSign;

    private String leaveType;

    private String leaveUserDepartment;

    private String proinsId;

    private String userName;

    private String deploymentName;

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLeaveNoteId() {
        return leaveNoteId;
    }

    public void setLeaveNoteId(Integer leaveNoteId) {
        this.leaveNoteId = leaveNoteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLeaveStartTime() {
        return leaveStartTime;
    }

    public void setLeaveStartTime(Date leaveStartTime) {
        this.leaveStartTime = leaveStartTime;
    }

    public Date getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(Date leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason == null ? null : leaveReason.trim();
    }

    public Date getLeaveCancleTime() {
        return leaveCancleTime;
    }

    public void setLeaveCancleTime(Date leaveCancleTime) {
        this.leaveCancleTime = leaveCancleTime;
    }

    public Integer getLeaveCancleSign() {
        return leaveCancleSign;
    }

    public void setLeaveCancleSign(Integer leaveCancleSign) {
        this.leaveCancleSign = leaveCancleSign;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType == null ? null : leaveType.trim();
    }

    public String getLeaveUserDepartment() {
        return leaveUserDepartment;
    }

    public void setLeaveUserDepartment(String leaveUserDepartment) {
        this.leaveUserDepartment = leaveUserDepartment == null ? null : leaveUserDepartment.trim();
    }

    public String getProinsId() {
        return proinsId;
    }

    public void setProinsId(String proinsId) {
        this.proinsId = proinsId == null ? null : proinsId.trim();
    }

    @Override
    public String toString() {
        return "LeaveNote{" +
                "leaveNoteId=" + leaveNoteId +
                ", userId=" + userId +
                ", leaveStartTime=" + leaveStartTime +
                ", leaveEndTime=" + leaveEndTime +
                ", leaveReason='" + leaveReason + '\'' +
                ", leaveCancleTime=" + leaveCancleTime +
                ", leaveCancleSign=" + leaveCancleSign +
                ", leaveType='" + leaveType + '\'' +
                ", leaveUserDepartment='" + leaveUserDepartment + '\'' +
                ", proinsId='" + proinsId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}