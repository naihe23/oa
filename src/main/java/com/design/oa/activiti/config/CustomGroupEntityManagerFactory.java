package com.design.oa.activiti.config;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomGroupEntityManagerFactory implements SessionFactory {

    @Resource
    private CustomGroupEntityManager customGroupEntityManager;

    public Class<?> getSessionType() {
        // 返回原始的GroupManager类型
        return GroupEntityManager.class;
    }

    public Session openSession() {
        // 返回自定义的GroupManager实例
        return customGroupEntityManager;
    }

    @Autowired
    public void setCustomGroupEntityManager(CustomGroupEntityManager customGroupEntityManager) {
        this.customGroupEntityManager = customGroupEntityManager;
    }
}
