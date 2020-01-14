package com.ad.system.secutity.filter;

import com.ad.system.model.SysUser;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KickoutSessionControlFilter extends AccessControlFilter {

    private String kickoutUrl; //踢出后到的地址

    private static final String SESSION_KICKOUT_KEY = "kickout";

    private SessionManager sessionManager;
    private Cache<String, Serializable> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_redis_cache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (AnonFilter.ALLOW_PASS_PERMISSION_VALUE.equals(request.getAttribute(AnonFilter.ALLOW_PASS_PERMISSION_KEY))
                && !getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        SysUser user = (SysUser) subject.getPrincipal();
        String username = user.getUserName();
        Serializable sessionIdNew = session.getId();

        Serializable sessionIdOld = cache.get(username);

        if (!sessionIdNew.equals(sessionIdOld) && ObjectUtils.isEmpty(session.getAttribute(SESSION_KICKOUT_KEY))) {
            try {
                cache.put(username, sessionIdNew);
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(sessionIdOld));
                if (!ObjectUtils.isEmpty(kickoutSession)) {
                    kickoutSession.setAttribute(SESSION_KICKOUT_KEY, true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        if (!ObjectUtils.isEmpty(session.getAttribute(SESSION_KICKOUT_KEY))) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);

            Map<String, String> resultMap = new HashMap<String, String>();
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                resultMap.put("user_status", "300");
                resultMap.put("message", "您已经在其他地方登录，请重新登录！");
                //输出json串
                out(response, resultMap);
            }else{
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSON.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }
    }
}
