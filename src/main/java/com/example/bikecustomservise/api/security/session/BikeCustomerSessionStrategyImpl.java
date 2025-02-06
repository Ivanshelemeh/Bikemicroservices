package com.example.bikecustomservise.api.security.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class BikeCustomerSessionStrategyImpl implements BikeCustomerSessionStrategy {

    private boolean contextRelative;

    @Override
    public void sendCustomerRedirect(HttpServletRequest request, HttpServletResponse response, String customUrl) throws IOException {
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), customUrl);
        redirectUrl = response.encodeRedirectURL(redirectUrl);
        if (log.isDebugEnabled()) {
            log.debug(String.valueOf(LogMessage.format("Redirecting to %s", redirectUrl)));
        }
        response.sendRedirect(redirectUrl);
    }

    protected String calculateRedirectUrl(String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (isContextRelative()) {
                return url;
            }
            return contextPath + url;
        }
        if (!isContextRelative()) {
            return url;
        }
        Assert.isTrue(url.contains(contextPath), "The fully qualified URL does not include context path.");
        url = url.substring(url.lastIndexOf("://") + 3);
        url = url.substring(url.indexOf(contextPath) + contextPath.length());
        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }
        return url;
    }

    protected boolean isContextRelative() {
        return this.contextRelative;
    }

    public void setContextRelative(boolean contextUrlRelative) {
        this.contextRelative = contextUrlRelative;
    }
}

