package com.design.oa.activiti.config;

import com.design.oa.dao.RoleMapper;
import com.design.oa.dao.UserMapper;
import com.design.oa.model.Role;
import com.design.oa.model.User;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CustomUserEntityManager extends UserEntityManager {

    private static final Log logger = LogFactory.getLog(CustomUserEntityManager.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserEntity findUserById(String userId) {
        UserEntity userEntity = new UserEntity();
        User cue = userMapper.selectByPrimaryKey(Integer.parseInt(userId));//这是我们的dao方法查询回来的方法，是自己定义的user
        userEntity = ActivitiUserUtils.toActivitiUser(cue);//将自定义的user转化为activiti的类
        return userEntity;//返回的是activiti的实体类
    }

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null)
            return null;

        List<Role> groupIds = roleMapper.getUserRolesMessage(Integer.parseInt(userCode));

        List<Group> gs = null;
        gs = ActivitiUserUtils.toActivitiGroups(groupIds);
        return gs;

    }
}
