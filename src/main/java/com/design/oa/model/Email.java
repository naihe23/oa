package com.design.oa.model;

import java.util.Date;

public class Email {
    private Integer emailId;

    private Integer userId;

    private String emailSender;

    private String emailRecipients;

    private Date emailSendTime;

    private String emailTheme;

    private Integer emailState;

    private String emailContent;

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender == null ? null : emailSender.trim();
    }

    public String getEmailRecipients() {
        return emailRecipients;
    }

    public void setEmailRecipients(String emailRecipients) {
        this.emailRecipients = emailRecipients == null ? null : emailRecipients.trim();
    }

    public Date getEmailSendTime() {
        return emailSendTime;
    }

    public void setEmailSendTime(Date emailSendTime) {
        this.emailSendTime = emailSendTime;
    }

    public String getEmailTheme() {
        return emailTheme;
    }

    public void setEmailTheme(String emailTheme) {
        this.emailTheme = emailTheme == null ? null : emailTheme.trim();
    }

    public Integer getEmailState() {
        return emailState;
    }

    public void setEmailState(Integer emailState) {
        this.emailState = emailState;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent == null ? null : emailContent.trim();
    }
}