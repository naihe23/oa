package com.design.oa.model;

import java.util.Date;

public class Incoming {
    private Integer incomingId;

    private Integer userId;

    private Date incomingDate;

    private String incomingTitle;

    private String incomingHandle;

    private String incomingDepar;

    private Integer incomingTextId;

    private Integer incomingState;

    private String departmentName;

    private String id;

    private String userName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private String textTitle;

    private String deploymentName;

    public String getDeploymentName() {
        return deploymentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIncomingId() {
        return incomingId;
    }

    public void setIncomingId(Integer incomingId) {
        this.incomingId = incomingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
        this.incomingDate = incomingDate;
    }

    public String getIncomingTitle() {
        return incomingTitle;
    }

    public void setIncomingTitle(String incomingTitle) {
        this.incomingTitle = incomingTitle == null ? null : incomingTitle.trim();
    }

    public String getIncomingHandle() {
        return incomingHandle;
    }

    public void setIncomingHandle(String incomingHandle) {
        this.incomingHandle = incomingHandle == null ? null : incomingHandle.trim();
    }

    public String getIncomingDepar() {
        return incomingDepar;
    }

    public void setIncomingDepar(String incomingDepar) {
        this.incomingDepar = incomingDepar == null ? null : incomingDepar.trim();
    }

    public Integer getIncomingTextId() {
        return incomingTextId;
    }

    public void setIncomingTextId(Integer incomingTextId) {
        this.incomingTextId = incomingTextId;
    }

    public Integer getIncomingState() {
        return incomingState;
    }

    public void setIncomingState(Integer incomingState) {
        this.incomingState = incomingState;
    }
}