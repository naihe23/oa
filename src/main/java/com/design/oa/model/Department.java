package com.design.oa.model;

import java.util.Date;
import java.util.List;

public class Department {
    private Integer departmentId;

    private Integer departmentSupid;

    private Integer adminId;

    private String departmentType;

    private String departmentSuperName;

    private String departmentName;

    private Date departmentCreateTime;

    private List<Department> children;

    public String getDepartmentSuperName() {
        return departmentSuperName;
    }

    public void setDepartmentSuperName(String departmentSuperName) {
        this.departmentSuperName = departmentSuperName;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentSupid() {
        return departmentSupid;
    }

    public void setDepartmentSupid(Integer departmentSupid) {
        this.departmentSupid = departmentSupid;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType == null ? null : departmentType.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getDepartmentCreateTime() {
        return departmentCreateTime;
    }

    public void setDepartmentCreateTime(Date departmentCreateTime) {
        this.departmentCreateTime = departmentCreateTime;
    }
}