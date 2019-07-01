package com.design.oa.shiro;


import com.design.oa.filter.RestFilter;

import com.design.oa.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirofiter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("corsFilter", new RestFilter());
        filters.put("logoutFilter",new MyLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String,String> shiroFilterMap = new LinkedHashMap<>();
        shiroFilterMap.put("/user/userLogin","corsFilter,anon");
        shiroFilterMap.put("/user/register","corsFilter,anon");
        shiroFilterMap.put("/modeler.html","corsFilter,anon");
        shiroFilterMap.put("/queryProPlan","corsFilter,anon");
        shiroFilterMap.put("/models/**","corsFilter,anon");
        shiroFilterMap.put("/service/**","corsFilter,anon");
        shiroFilterMap.put("/service/editor/stencilset","corsFilter,anon");
        shiroFilterMap.put("/editor-app/**","corsFilter,anon");
        shiroFilterMap.put("/user/logout","corsFilter,logoutFilter,logout");
        shiroFilterMap.put("/**","corsFilter,authc");

        shiroFilterFactoryBean.setLoginUrl("/user/unauth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher   =new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager  sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(60000000);

        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }


    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:shiro/shiro-ehcache.xml");
        return ehCacheManager;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager  = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

}
