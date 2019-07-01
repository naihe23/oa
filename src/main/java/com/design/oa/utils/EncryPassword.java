package com.design.oa.utils;

import com.design.oa.mailModel.JamesUser;
import com.design.oa.model.Admin;
import com.design.oa.model.User;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

public class EncryPassword {

    public static User encryptedUserPassword(User user){
        String salt = UUID.randomUUID().toString();
        Md5Hash md5Hash = new Md5Hash(user.getUserPassword(),salt,2);
        user.setUserPassword(md5Hash.toString());
        user.setUserSalt(salt);
        return user;
    }
    public static Admin encryptedAdminPassword(Admin admin){
        String salt = UUID.randomUUID().toString();
        Md5Hash md5Hash = new Md5Hash(admin.getAdminPassword(),salt,2);
        admin.setAdminPassword(md5Hash.toString());
        admin.setAdminSalt(salt);
        return admin;
    }

    public static JamesUser encryptedJamesUserPassword(JamesUser jamesUser){
        Md5Hash md5Hash = new Md5Hash(jamesUser.getPassword(),null,1);
        jamesUser.setPassword(md5Hash.toString());
        return jamesUser;
    }

}
