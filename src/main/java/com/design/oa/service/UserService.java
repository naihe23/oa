package com.design.oa.service;

import com.design.oa.mailModel.JamesUser;
import com.design.oa.model.*;

import java.util.List;

public interface UserService {
     User findUserByUserName(String userName);
     int insertUser(User user, int[] userRole, JamesUser users);

    List<UserAuthority> getAllUser(String userName);
    int deleteUser(int userId,String userPassword);

    int updateUser(User user, int[] userRole);

    int lockedUser(User user);

    List<Role> getRoles(Integer userId);

    List<Menu>  getUserAllMenus();

    List<Role> getAllRoles(String roleName);

    User getUserInformation();

    int updateUserInfo(User user);

    int editUserPassword(String userName,Integer userId, String userNewPassword) throws Exception;
}
