package com.design.oa.service.impl;

import com.design.oa.dao.DepartmentMapper;
import com.design.oa.model.Admin;
import com.design.oa.model.Department;
import com.design.oa.service.DepartmentService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImp implements DepartmentService {

    @Resource
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartment( String departmentName) {
        List<Department> departmentList = departmentMapper.getAllDepartment(departmentName);
        return departmentList;
    }

    @Override
    public List<Department> getTreeDepartment(int supid) {
        List<Department> departmentList= departmentMapper.getTreeDepartment(supid);
        if(departmentList!=null&&departmentList.size()!=0) {
            for (Department department : departmentList) {
                List<Department> departmentList1 = getTreeDepartment(department.getDepartmentId());
                department.setChildren(departmentList1);
            }
        }
        return departmentList;
    }

    @Override
    public int addDepartment(Department department) {
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        department.setDepartmentCreateTime(new Date());
        department.setAdminId(admin.getAdminId());
        int state = departmentMapper.insertSelective(department);
        if(state>0){
            return 201;
        }
        return 401;
    }

    @Override
    public int updateDepartment(Department department) {

        int state = departmentMapper.updateByPrimaryKeySelective(department);

        if(state>0){
            return 201;
        }
        return 401;
    }

    @Override
    public int deleteDepartment(int departmentId) {
        if(departmentId==1){
            return 402;
        }
        int state = departmentMapper.deleteByPrimaryKey(departmentId);
        if(state>0){
            return 201;
        }
        return 0;
    }

}
