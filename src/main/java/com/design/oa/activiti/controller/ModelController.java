package com.design.oa.activiti.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.dao.RoleMapper;
import com.design.oa.dao.UserMapper;
import com.design.oa.model.Role;
import com.design.oa.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/service/models")
@RestController
public class ModelController {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @GetMapping("/getUserList")
    public HashMap<String,Object> getUserList() {
        HashMap<String,Object> map = new HashMap<>();
        List<User> users = null;
        users = userMapper.selectAllUsers();
        map.put("rows",users);
        return map;
    }
    @PostMapping("/getProjectList")
    public HashMap<String, Object> getProjectList() {
        JSONObject jsonObject = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 11);
        map.put("name", "mailtest111");
        jsonObject.put("rows",map);
        return map;
    }
    @GetMapping("/getGroupList")
    public HashMap<String,Object> getGroupList() {
        HashMap<String,Object> map = new HashMap<>();
        List<Role> roleList = null;
        roleList = roleMapper.seleceAllRoles();
        map.put("rows",roleList);
        return map;
    }
}
