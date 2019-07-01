package com.design.oa.service.impl;

import com.design.oa.dao.*;
import com.design.oa.mailDao.JamesUserMapper;
import com.design.oa.mailModel.JamesUser;
import com.design.oa.model.*;
import com.design.oa.service.UserService;
import com.design.oa.utils.EncryPassword;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    JamesUserMapper jamesUserMapper;

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(User user, int[] userRole, JamesUser users) {
        User user1 = EncryPassword.encryptedUserPassword(user);
        JamesUser jamesUser = EncryPassword.encryptedJamesUserPassword(users);
        User isExitUser = userMapper.selectByUserName(user.getUserName());
        JamesUser jamesUser1 = jamesUserMapper.selectByPrimaryKey(users.getUserName());
        if (isExitUser == null&&jamesUser1==null) {
            int userId = userMapper.insertSelective(user1);
            int userRoleId = userRoleMapper.insert(user1.getUserId(), userRole);
            int emailInsert = jamesUserMapper.insertSelective(jamesUser);
            if (userId > 0 && userRoleId > 0 && emailInsert > 0) {
                return 201;
            } else
                return 401;
        } else
            return 402;
    }

    @Override
    public List<UserAuthority> getAllUser(String userName) {
        List<UserAuthority> list = userMapper.getAllUser(userName);
        for (UserAuthority userAuthority : list) {
            userAuthority.setRoleName(userMapper.getAllUserRole(userAuthority.getUserId()));
            userAuthority.setDepartmentName(userMapper.getAllUserDepar(userAuthority.getUserId()));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteUser(int userId,String userPassword) {
        User user = userMapper.selectByPrimaryKey(userId);
        int isUserDelete = userMapper.deleteByPrimaryKey(userId);
        int isUserRoleDelete = userRoleMapper.deleteByUserId(userId);
        int deleteMailUser = jamesUserMapper.deleteByPrimaryKey(user.getUserEmail());
//        List<Map<String, Object>> inboxList = emailService.getInbox(user.getUserEmail(),userPassword);
//        for(Map<String, Object> map:inboxList){
//            int mailNumber =(int) map.get("mailNumber");
//            emailService.deleteInobxMail(mailNumber,user.getUserEmail(),userPassword);
//        }
//        List<Map<String, Object>> outboxList = emailService.getOutbox(user.getUserEmail(),userPassword);
//        for(Map<String, Object> map:outboxList){
//            int mailNumber =(int) map.get("mailNumber");
//            emailService.deleteOutBoxMail(mailNumber,user.getUserEmail(),userPassword);
//        }
        if (isUserDelete > 0 && isUserRoleDelete > 0 && deleteMailUser > 0 ) {
            return 201;
        } else
            return 401;
    }

    @Override
    public int updateUser(User user, int[] userRole) {
        int isUpdate = userMapper.updateByPrimaryKeySelective(user);
        int isDelete = userRoleMapper.deleteByUserId(user.getUserId());
        int isInsert = userRoleMapper.insert(user.getUserId(), userRole);
        if (isUpdate > 0 && isDelete > 0 && isInsert > 0) {
            return 201;
        } else
            return 401;
    }


    @Override
    public int lockedUser(User user) {
        int isLocked = userMapper.updateByPrimaryKeySelective(user);
        if (isLocked > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    public List<Role> getRoles(Integer userId) {
        List<Role> rolesList = userMapper.getUserRoles(userId);
        return rolesList;
    }

    @Override
    public List<Menu> getUserAllMenus() {
        List<Menu> menuList = new ArrayList<>();
        Object object = (Object) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (object instanceof User) {
            User user = (User) object;
            menuList = menuMapper.getUserAllMenus(user.getUserId());
        } else if (object instanceof Admin) {
            Admin admin = (Admin) object;
            menuList = menuMapper.getAdminAllMenus(admin.getAdminId());
        }
        return menuList;
    }

    @Override
    public List<Role> getAllRoles(String roleName) {
        List<Role> roleList = roleMapper.selectAllRolesOrByRoleName(roleName);
        return roleList;
    }

    @Override
    public User getUserInformation() {
        Object object = (Object) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (object instanceof User) {
            User user = (User) object;
            return user;
        } else if (object instanceof Admin) {
            Admin admin = (Admin) object;
            return null;
        }
        return null;
    }

    @Override
    public int updateUserInfo(User user) {
        int isUpdate = userMapper.updateByPrimaryKeySelective(user);
        if (isUpdate > 0) {
            return 201;
        }
        return 401;
    }

    @Override
    public int editUserPassword(String userName,Integer userId, String userNewPassword)throws Exception {
        User user = new User();
        JamesUser jamesUser = new JamesUser();
        user.setUserId(userId);
        user.setUserPassword(userNewPassword);
        user = EncryPassword.encryptedUserPassword(user);
        jamesUser.setUserName(userName+"@oa.cn");
        jamesUser.setPassword(userNewPassword);
        JamesUser jamesUser1 = EncryPassword.encryptedJamesUserPassword(jamesUser);
        int state = userMapper.updateByPrimaryKeySelective(user);
        int updateUsers = jamesUserMapper.updateByPrimaryKeySelective(jamesUser1);
        if (state > 0&&updateUsers>0) {
            return 201;
        } else
            return 402;
    }


}
