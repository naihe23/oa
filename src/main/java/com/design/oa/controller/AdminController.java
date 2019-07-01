package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.mailModel.JamesUser;
import com.design.oa.model.*;
import com.design.oa.service.AdminService;
import com.design.oa.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final String START_PASSWORD = "123456";
    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @RequestMapping("/register")
    public int adminRegister(Admin admin) {
        return adminService.register(admin);
    }

    @PostMapping(value = "/users")
    public String getAllUsers(String curPage, String userName) {
        Integer curpage = Integer.parseInt(curPage);
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(curpage, 4);
        List<UserAuthority> list = userService.getAllUser(userName);
        PageInfo<UserAuthority> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        if (list.isEmpty()) {
            jsonObject.put("msg", "无用户");
        } else {
            jsonObject.put("msg", pageList);
        }
        return jsonObject.toString();
    }


    @PostMapping("/user")
    public String AddUser (String userName, String userAddress, String userPhone, String userDepartment, int[] userRole) throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        JamesUser users = new JamesUser();
        user.setUserName(userName);
        user.setUserAddress(userAddress);
        user.setAdminId(2);
        user.setUserPhone(userPhone);
        user.setUserEmail(userName+"@oa.cn");
        user.setUserPassword(START_PASSWORD);
        user.setDepartmentId(Integer.parseInt(userDepartment));
        users.setUserName(userName+"@oa.cn");
        users.setPassword(START_PASSWORD);
        users.setPasswordHashAlgorithm("MD5");
        users.setVersion(1);
        int isInsert = userService.insertUser(user, userRole,users);
        jsonObject.put("msg", isInsert);
        return jsonObject.toString();
    }

    @PostMapping("delete/user")
    public String deleteUser(String userId,String userPassword) {
        JSONObject jsonObject = new JSONObject();
        int isDelete = userService.deleteUser(Integer.parseInt(userId),userPassword);
        jsonObject.put("msg", isDelete);
        return jsonObject.toString();
    }


    @PostMapping("update/user")
    public String updateUser(String userId, String userName, String userAddress, String userPhone, String userDepartment, int[] userRole) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserName(userName);
        user.setUserAddress(userAddress);
        user.setUserPhone(userPhone);
        user.setDepartmentId(Integer.parseInt(userDepartment));
        int isInsert = userService.updateUser(user, userRole);
        jsonObject.put("msg", isInsert);
        return jsonObject.toString();
    }

    @GetMapping("/reset")
    public String reset(int userId)throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user  =(User)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        int state = userService.editUserPassword(user.getUserName(),user.getUserId(),START_PASSWORD);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/locked")
    public String locked(int userId) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setUserId(userId);
        user.setUserIslocked(2);
        int isLocked = userService.lockedUser(user);
        jsonObject.put("msg", isLocked);
        return jsonObject.toString();
    }

    @GetMapping("/unLocked")
    public String unLocked(int userId) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setUserId(userId);
        user.setUserIslocked(1);
        int isLocked = userService.lockedUser(user);
        jsonObject.put("msg", isLocked);
        return jsonObject.toString();
    }

    @GetMapping("/allRoles")
    public String getChooseAllRoles(){
        JSONObject jsonObject = new JSONObject();
        List<Role> roleList = userService.getAllRoles(null);
        if (roleList.isEmpty()) {
            jsonObject.put("msg", "无用户");
        } else {
            jsonObject.put("msg", roleList);
        }
        return jsonObject.toString();
    }

    @GetMapping("/message")
    public String message(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();

        jsonObject.put("msg", subject.getPrincipals());
        return jsonObject.toString();
    }

}
