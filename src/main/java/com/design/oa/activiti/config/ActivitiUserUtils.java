package com.design.oa.activiti.config;

import com.design.oa.model.Role;
import com.design.oa.model.User;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;

public class ActivitiUserUtils {
	public static UserEntity toActivitiUser(User bUser){
        UserEntity userEntity = new UserEntity();  
        userEntity.setId(bUser.getUserId().toString());
        userEntity.setFirstName(bUser.getUserName());
        userEntity.setLastName(bUser.getUserName());
        userEntity.setPassword(bUser.getUserPassword());
        userEntity.setEmail(bUser.getUserEmail());
        userEntity.setRevision(1);  
        return userEntity;  
    }  
  
    public static GroupEntity toActivitiGroup(Role sysRole){
        GroupEntity groupEntity = new GroupEntity();  
        groupEntity.setRevision(1);  
        groupEntity.setType("assignment");  
        groupEntity.setId(sysRole.getRoleId().toString());
        groupEntity.setName(sysRole.getRoleName());
        return groupEntity;  
    }  
  
    public static List<Group> toActivitiGroups(List<Role> sysRoles){
        List<Group> groups = new ArrayList<Group>();
        for (Role sysRole : sysRoles) {  
            GroupEntity groupEntity = toActivitiGroup(sysRole);  
            groups.add(groupEntity);  
        }  
        return groups;  
    }
 
}
