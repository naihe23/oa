package com.design.oa.model;

public class EmialAttachment {
    private Integer emailAttachmentId;

    private Integer emailId;

    private String emailAttachmentName;

    private String emailAttachmentUrl;

    public Integer getEmailAttachmentId() {
        return emailAttachmentId;
    }

    public void setEmailAttachmentId(Integer emailAttachmentId) {
        this.emailAttachmentId = emailAttachmentId;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getEmailAttachmentName() {
        return emailAttachmentName;
    }

    public void setEmailAttachmentName(String emailAttachmentName) {
        this.emailAttachmentName = emailAttachmentName == null ? null : emailAttachmentName.trim();
    }

    public String getEmailAttachmentUrl() {
        return emailAttachmentUrl;
    }

    public void setEmailAttachmentUrl(String emailAttachmentUrl) {
        this.emailAttachmentUrl = emailAttachmentUrl == null ? null : emailAttachmentUrl.trim();
    }
}