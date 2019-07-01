package com.design.oa.model;

import java.util.Date;

public class AttendanceAdmin {
    private Integer attAdminId;

    private Integer adminId;

    private Integer attAdminLength;

    private Date attAdminMor;

    private Date attAdminAfter;

    private String attAdminAdd;

    public Integer getAttAdminId() {
        return attAdminId;
    }

    public void setAttAdminId(Integer attAdminId) {
        this.attAdminId = attAdminId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAttAdminLength() {
        return attAdminLength;
    }

    public void setAttAdminLength(Integer attAdminLength) {
        this.attAdminLength = attAdminLength;
    }

    public Date getAttAdminMor() {
        return attAdminMor;
    }

    public void setAttAdminMor(Date attAdminMor) {
        this.attAdminMor = attAdminMor;
    }

    public Date getAttAdminAfter() {
        return attAdminAfter;
    }

    public void setAttAdminAfter(Date attAdminAfter) {
        this.attAdminAfter = attAdminAfter;
    }

    public String getAttAdminAdd() {
        return attAdminAdd;
    }

    public void setAttAdminAdd(String attAdminAdd) {
        this.attAdminAdd = attAdminAdd == null ? null : attAdminAdd.trim();
    }

    @Override
    public String toString() {
        return "AttendanceAdmin{" +
                "attAdminId=" + attAdminId +
                ", adminId=" + adminId +
                ", attAdminLength=" + attAdminLength +
                ", attAdminMor=" + attAdminMor +
                ", attAdminAfter=" + attAdminAfter +
                ", attAdminAdd='" + attAdminAdd + '\'' +
                '}';
    }
}