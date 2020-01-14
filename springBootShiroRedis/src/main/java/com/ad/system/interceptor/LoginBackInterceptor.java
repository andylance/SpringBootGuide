package com.ad.system.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截
 * 每次提交表单login,退出当前登陆用户
 * 解决回退时重新登陆要登陆2次的情况
 */
@Component
public class LoginBackInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null && subject.isAuthenticated()){
			subject.logout();
		}
		return true;
	}
}
