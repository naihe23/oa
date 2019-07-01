package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.model.Department;
import com.design.oa.model.UserAuthority;
import com.design.oa.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    DepartmentService departmentService;

    @PostMapping("/departments")
    public String getAllDepartment(String curPage,String departmentName){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(Integer.parseInt(curPage), 4);
        List<Department> departmentList = departmentService.getAllDepartment(departmentName);
        PageInfo<Department> pageList = new PageInfo<>(departmentList);
        PageHelper.clearPage();
        jsonObject.put("msg", pageList);
        return jsonObject.toString();
    }

    @GetMapping("/treeDepartment")
    public String getTreeDepartment(){
        JSONObject jsonObject = new JSONObject();
        List<Department> departmentList = departmentService.getTreeDepartment(0);
        jsonObject.put("msg", departmentList);
        return jsonObject.toString();
    }

    @PostMapping("/department")
    public String addDepartment(@RequestBody Department department){
        JSONObject jsonObject = new JSONObject();
        int state = departmentService.addDepartment(department);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/update/department")
    public String updateDepartment(@RequestBody Department department){
        JSONObject jsonObject = new JSONObject();
        int state = departmentService.updateDepartment(department);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/delete/department")
    public String updateDepartment(int departmentId){
        JSONObject jsonObject = new JSONObject();
        int state = departmentService.deleteDepartment(departmentId);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }
}
