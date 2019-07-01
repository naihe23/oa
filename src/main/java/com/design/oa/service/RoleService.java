package com.design.oa.service;

import com.design.oa.model.Menu;

import java.util.List;

public interface RoleService {
    List<Menu> getMenus(int roleId);
}
