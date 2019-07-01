package com.design.oa.dao;

import com.design.oa.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getUserAllMenus(Integer userId);

    List<Menu> getAdminAllMenus(Integer adminId);

    List<Menu> searchUserTreeById(@Param("pId") int menuId);
}