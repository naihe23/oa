package com.design.oa.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class CustomDefaultWebSessionManager extends DefaultWebSessionManager {

    /**
     *  
     * 获取session id
     * 前后端分离将从请求头中获取jsesssionid
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 从请求头中获取token
        String token = WebUtils.toHttp(request).getHeader("Authorization");
        // 判断是否有值
        if (StringUtils.isEmpty(token)) {
            // 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }
        // 若header获取不到token则尝试从cookie中获取
        return super.getSessionId(request, response);
    }
}