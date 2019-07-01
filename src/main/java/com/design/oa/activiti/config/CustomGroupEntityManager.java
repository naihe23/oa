package com.design.oa.activiti.config;

import com.design.oa.dao.RoleMapper;
import com.design.oa.dao.UserMapper;
import com.design.oa.model.Role;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CustomGroupEntityManager  extends GroupEntityManager {

    private static final Log logger = LogFactory.getLog(CustomGroupEntityManager.class);

  //用于查询实际业务中用户表、角色等表
    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null)
            return null;

        List<Role> bGroupList = roleMapper.getUserRolesMessage(Integer.parseInt(userCode));

        List<Group> gs = new java.util.ArrayList<>();
        GroupEntity g;
        String roleId;
        String activitRole;
        for (Role bGroup : bGroupList) {
            g = new GroupEntity();
            g.setRevision(1);
            g.setType("assignment");
            roleId = String.valueOf(bGroup.getRoleId());
//            activitRole = bindGroupWithRole.get(roleId);//此处只是根据RoleId获取RoleCode， 因实际表中无RoleCode字段，暂且如此实际，此行可注释掉
            g.setId(/*activitRole != null ? activitRole :*/ roleId);
            g.setName(bGroup.getRoleName());
            gs.add(g);
        }
        return gs;
    }
}

