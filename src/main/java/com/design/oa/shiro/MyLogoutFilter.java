package com.design.oa.shiro;


import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义退出的拦截器，退出返回json
 * @author 35168
 *
 */
public class MyLogoutFilter extends LogoutFilter{
    private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("msg", "成功");
        out.println(result);
        out.flush();
        out.close();
        return false;
    }
}
