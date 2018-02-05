package com.sun.discipline.web.controller;

import com.sun.discipline.domain.common.JsonResponse;
import com.sun.discipline.utils.math.UUIDUtils;
import com.sun.discipline.web.config.SecurityProperties;
import lombok.experimental.PackagePrivate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunjian.
 */
@RestController
@RequestMapping("/public")
public class PublicController
{
    private final SecurityProperties securityProperties;
    private RequestCache requestCache = new HttpSessionRequestCache();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public PublicController(SecurityProperties securityProperties)
    {
        this.securityProperties = securityProperties;
    }

    @RequestMapping("/hello")
    public String sayHello()
    {
        return "This is public Hello World!      " + UUIDUtils.getUUID();
    }

    @RequestMapping("/logout")
    public String logout()
    {
        return "退出登录!";
    }

    @RequestMapping("/login")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public JsonResponse login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null)
        {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("请求登录的url" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
            {
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowserProperties().getLoginPage());
            }
        }
        return JsonResponse.failureJsonResponse("请登录");
    }
}
