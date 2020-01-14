package com.ad.system.secutity.filter;

import org.apache.shiro.web.filter.authc.AnonymousFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AnonFilter extends AnonymousFilter {
    public static final String ALLOW_PASS_PERMISSION_KEY = "ALLOW_PASS_PERMISSION_KEY";
    public static final String ALLOW_PASS_PERMISSION_VALUE = "Y";

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        request.setAttribute(ALLOW_PASS_PERMISSION_KEY, ALLOW_PASS_PERMISSION_VALUE);
        return super.onPreHandle(request, response, mappedValue);
    }
}
