package com.design.oa.service;

import com.design.oa.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartment(String departmentName);

    List<Department> getTreeDepartment(int supid);

    int addDepartment(Department department);

    int updateDepartment(Department department);

    int deleteDepartment(int departmentId);
}
