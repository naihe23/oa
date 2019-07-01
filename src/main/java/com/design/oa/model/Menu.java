package com.design.oa.model;

import java.util.List;

public class Menu {
    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer menuFathermenu;

    private Integer menuState;
    private List<Menu> children;

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuFathermenu() {
        return menuFathermenu;
    }

    public void setMenuFathermenu(Integer menuFathermenu) {
        this.menuFathermenu = menuFathermenu;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }
}