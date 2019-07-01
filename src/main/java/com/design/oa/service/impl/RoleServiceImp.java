package com.design.oa.service.impl;

import com.design.oa.dao.RoleMenuMapper;
import com.design.oa.model.Menu;
import com.design.oa.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> getMenus(int roleId) {
        List<Menu> menuList = roleMenuMapper.getMenus(roleId);
        return menuList;
    }
}
