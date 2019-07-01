package com.design.oa.model;

public class Contact {
    private Integer contactId;

    private Integer userId;

    private String contactName;

    private Long contactPhone;

    private String contactAddress;

    public Integer getContactId() {
        return contactId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", userId=" + userId +
                ", contactName='" + contactName + '\'' +
                ", contactPhone=" + contactPhone +
                ", contactAddress='" + contactAddress + '\'' +
                '}';
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public Long getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Long contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }
}