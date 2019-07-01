package com.design.oa.controller;


import com.alibaba.fastjson.JSONObject;
import com.design.oa.model.Menu;
import com.design.oa.model.User;
import com.design.oa.service.ContactService;
import com.design.oa.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    ContactService contactService;

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        Subject currentUser  = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPassword());
        try {
            currentUser.login(token);
            Subject subject = SecurityUtils.getSubject();
            List<Menu> menuList = userService.getUserAllMenus();
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("menuList",menuList);
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被锁定");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @GetMapping("/information")
    public HashMap<String,User> getUserInformaion(){
        HashMap<String,User> map= new HashMap<>();
        User user = userService.getUserInformation();
        map.put("msg", user);
        return map;
    }
    @RequestMapping("/unauth")
    public String unauth(){
        JSONObject jsonObject = new JSONObject();
        System.out.println("未登录");
        jsonObject.put("msg", "未登录");
        return jsonObject.toString();
    }

    @PostMapping("/userInfor")
    public String editUserInfor(@RequestBody User user){
        JSONObject jsonObject = new JSONObject();
        int isUpdate= userService.updateUserInfo(user);
        jsonObject.put("msg", isUpdate);
        return jsonObject.toString();
    }

    @PostMapping("/password")
    public String editPassword(String userNewPassword) throws Exception{
        JSONObject jsonObject = new JSONObject();
        User user  =(User)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        int state = userService.editUserPassword(user.getUserName(),user.getUserId(),userNewPassword);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }
}
