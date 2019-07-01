package com.design.oa.activiti.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext1;
    /**
     * * 设置当前上下文环境，此方法由spring自动装配
     *
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext1 = applicationContext;
    }

    /**
     * 从当前IOC获取bean
     *
     * @param id
     * bean的id
     * @return
     *
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext1.getBean(id);
        return object;
    }


}
