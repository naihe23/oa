package com.design.oa.model;

import java.util.Date;

public class AttendanceRecord {
    private Integer attRecordId;

    private Integer userId;

    private Date attDate;

    private Date attWorkTime;

    private Date attOffTime;

    private String attState;

    private String attRepairReason;

    private String attRepairType;

    public Integer getAttRecordId() {
        return attRecordId;
    }

    public void setAttRecordId(Integer attRecordId) {
        this.attRecordId = attRecordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public Date getAttWorkTime() {
        return attWorkTime;
    }

    public void setAttWorkTime(Date attWorkTime) {
        this.attWorkTime = attWorkTime;
    }

    public Date getAttOffTime() {
        return attOffTime;
    }

    public void setAttOffTime(Date attOffTime) {
        this.attOffTime = attOffTime;
    }

    public String getAttState() {
        return attState;
    }

    public void setAttState(String attState) {
        this.attState = attState == null ? null : attState.trim();
    }

    public String getAttRepairReason() {
        return attRepairReason;
    }

    public void setAttRepairReason(String attRepairReason) {
        this.attRepairReason = attRepairReason == null ? null : attRepairReason.trim();
    }

    public String getAttRepairType() {
        return attRepairType;
    }

    public void setAttRepairType(String attRepairType) {
        this.attRepairType = attRepairType == null ? null : attRepairType.trim();
    }
}