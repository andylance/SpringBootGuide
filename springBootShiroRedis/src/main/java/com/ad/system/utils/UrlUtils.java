package com.ad.system.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlUtils {
    public static String getCurrentURI(String currentUrl, String contextName) {
        if (!StringUtils.isBlank(contextName)) {
            currentUrl = currentUrl.substring(currentUrl.indexOf(contextName) + contextName.length());
        }
        if (StringUtils.isNotBlank(currentUrl) && currentUrl.startsWith("/")) {
            currentUrl = currentUrl.replaceFirst("/", "");
        }
        currentUrl = StringUtils.replace(currentUrl,"/", ":" );
        return currentUrl;
    }
}
