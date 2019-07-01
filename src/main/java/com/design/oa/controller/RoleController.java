package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.model.Menu;
import com.design.oa.model.Role;
import com.design.oa.service.AdminService;
import com.design.oa.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class RoleController {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @PostMapping("/roles")
    public HashMap<String,Object> getAllRoles(String curPage, String roleName){
        HashMap<String,Object> map= new HashMap<>();
        PageHelper.startPage(Integer.parseInt(curPage), 4);
        List<Role> roleList = userService.getAllRoles(roleName);
        PageInfo<Role> pageList = new PageInfo<>(roleList);
        PageHelper.clearPage();
        if (roleList.isEmpty()) {
            map.put("msg", "无用户");
        } else {
            map.put("msg", pageList);
        }
        return map;
    }

    @GetMapping("/allMenu")
    public String getAllMenu(){
        JSONObject jsonObject = new JSONObject();
        List<Menu> menuTreeList= adminService.searchUserTreeById(0);
        jsonObject.put("msg", menuTreeList);
        return jsonObject.toString();
    }

    @PostMapping("/role")
    public String addRole(String roleName,String roleDescribe,int[] roleMenu){
        JSONObject jsonObject = new JSONObject();
        int isAdd =  adminService.addRole(roleName,roleDescribe,roleMenu);
        jsonObject.put("msg", isAdd);
        return jsonObject.toString();
    }

    @PostMapping("/update/role")
    public String updateRole(int roleId,String roleName,String roleDescribe,int [] roleMenu){
        JSONObject jsonObject = new JSONObject();
        int isUpdate =  adminService.updateRole(roleId,roleName,roleDescribe,roleMenu);
        jsonObject.put("msg", isUpdate);
        return jsonObject.toString();
    }
    @PostMapping("/delete/role")
    public String deleteRole(int roleId){
        JSONObject jsonObject = new JSONObject();
        int isDelete = adminService.deleteRole(roleId);
        jsonObject.put("msg", isDelete);
        return jsonObject.toString();
    }

    @GetMapping("/role/locked")
    public String roleLocked(int roleId){
        JSONObject jsonObject = new JSONObject();
        int isLocked = adminService.roleLocked(roleId);
        jsonObject.put("msg", isLocked);
        return jsonObject.toString();
    }

    @GetMapping("/role/unLocked")
    public String roleUnLocked(int roleId){
        System.out.println("roleId:"+roleId);
        JSONObject jsonObject = new JSONObject();
        int isLocked = adminService.roleUnLocked(roleId);
        jsonObject.put("msg", isLocked);
        return jsonObject.toString();
    }
}
