package com.design.oa.model;

import java.util.Date;

public class OutgoingMessage {
    private Integer outgoingMessageId;

    private Integer userId;

    private Date outgoingMessageDate;

    private String outgoingMessageSecret;

    private String outgoingMessageUrgent;

    private Integer outgoingMessageNumber;

    private String outgoingMessageDep;

    private String outgoingMessageTitle;

    private String outgoingMessageScope;

    private String outgoingMessageHandle;

    private String outgoingMessageCountersign;

    private String outgoingMessageIssue;

    private String outgoingMessageTextId;

    private Integer outgoingMessageState;

    private String userName;

    private String departmentName;

    private String textTitle;

    private String deploymentName;

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOutgoingMessageId() {
        return outgoingMessageId;
    }

    public void setOutgoingMessageId(Integer outgoingMessageId) {
        this.outgoingMessageId = outgoingMessageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOutgoingMessageDate() {
        return outgoingMessageDate;
    }

    public void setOutgoingMessageDate(Date outgoingMessageDate) {
        this.outgoingMessageDate = outgoingMessageDate;
    }

    public String getOutgoingMessageSecret() {
        return outgoingMessageSecret;
    }

    public void setOutgoingMessageSecret(String outgoingMessageSecret) {
        this.outgoingMessageSecret = outgoingMessageSecret == null ? null : outgoingMessageSecret.trim();
    }

    public String getOutgoingMessageUrgent() {
        return outgoingMessageUrgent;
    }

    public void setOutgoingMessageUrgent(String outgoingMessageUrgent) {
        this.outgoingMessageUrgent = outgoingMessageUrgent == null ? null : outgoingMessageUrgent.trim();
    }

    public Integer getOutgoingMessageNumber() {
        return outgoingMessageNumber;
    }

    public void setOutgoingMessageNumber(Integer outgoingMessageNumber) {
        this.outgoingMessageNumber = outgoingMessageNumber;
    }

    public String getOutgoingMessageDep() {
        return outgoingMessageDep;
    }

    public void setOutgoingMessageDep(String outgoingMessageDep) {
        this.outgoingMessageDep = outgoingMessageDep == null ? null : outgoingMessageDep.trim();
    }

    public String getOutgoingMessageTitle() {
        return outgoingMessageTitle;
    }

    public void setOutgoingMessageTitle(String outgoingMessageTitle) {
        this.outgoingMessageTitle = outgoingMessageTitle == null ? null : outgoingMessageTitle.trim();
    }

    public String getOutgoingMessageScope() {
        return outgoingMessageScope;
    }

    public void setOutgoingMessageScope(String outgoingMessageScope) {
        this.outgoingMessageScope = outgoingMessageScope == null ? null : outgoingMessageScope.trim();
    }

    public String getOutgoingMessageHandle() {
        return outgoingMessageHandle;
    }

    public void setOutgoingMessageHandle(String outgoingMessageHandle) {
        this.outgoingMessageHandle = outgoingMessageHandle == null ? null : outgoingMessageHandle.trim();
    }

    public String getOutgoingMessageCountersign() {
        return outgoingMessageCountersign;
    }

    public void setOutgoingMessageCountersign(String outgoingMessageCountersign) {
        this.outgoingMessageCountersign = outgoingMessageCountersign == null ? null : outgoingMessageCountersign.trim();
    }

    public String getOutgoingMessageIssue() {
        return outgoingMessageIssue;
    }

    public void setOutgoingMessageIssue(String outgoingMessageIssue) {
        this.outgoingMessageIssue = outgoingMessageIssue == null ? null : outgoingMessageIssue.trim();
    }

    public String getOutgoingMessageTextId() {
        return outgoingMessageTextId;
    }

    public void setOutgoingMessageTextId(String outgoingMessageTextId) {
        this.outgoingMessageTextId = outgoingMessageTextId == null ? null : outgoingMessageTextId.trim();
    }

    public Integer getOutgoingMessageState() {
        return outgoingMessageState;
    }

    public void setOutgoingMessageState(Integer outgoingMessageState) {
        this.outgoingMessageState = outgoingMessageState;
    }
}