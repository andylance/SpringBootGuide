package com.ad.system.secutity.filter;

import com.ad.system.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class PermissionAuthcFilter extends AccessControlFilter {
    private String unauthorizedUrl;

    public PermissionAuthcFilter() {
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (AnonFilter.ALLOW_PASS_PERMISSION_VALUE.equals(request.getAttribute(AnonFilter.ALLOW_PASS_PERMISSION_KEY))) {
            return true;
        }
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject currenUser = this.getSubject(request, response);
        if (!currenUser.isAuthenticated()) {
            return true;
        } else {
            HttpServletRequest hreq = (HttpServletRequest) request;
            String currentURL = hreq.getRequestURI();
            String contextName = hreq.getContextPath();
            currentURL = UrlUtils.getCurrentURI(currentURL, contextName);
            try {
                if (StringUtils.isNotBlank(currentURL)) {
                    currenUser.checkPermission(currentURL);
                }

                return true;
            } catch (UnauthorizedException var8) {
                if (org.apache.shiro.util.StringUtils.hasText(this.unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, this.unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }

                return false;
            }
        }
    }

    public String getUnauthorizedUrl() {
        return this.unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }
}
