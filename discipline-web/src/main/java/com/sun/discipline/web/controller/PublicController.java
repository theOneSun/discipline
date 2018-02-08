package com.sun.discipline.web.controller;

import com.sun.discipline.domain.common.JsonResponse;
import com.sun.discipline.domain.common.User;
import com.sun.discipline.utils.math.UUIDUtils;
import com.sun.discipline.web.config.SecurityProperties;
import lombok.experimental.PackagePrivate;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowserProperties()
                                                                                   .getLoginPage());
            }
        }
        return JsonResponse.failureJsonResponse("请登录");
    }

    //注册
    @RequestMapping("/signUp")
    public String signUp(@RequestParam("code") String username, @RequestParam("password") String password)
    {
        System.out.println("username: " + username);
        return username;
    }

    //模拟登录页判断重定向
    @RequestMapping("/judgePage")
    public void judgePage(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
//        HttpSession session = request.getSession();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //未登录的用户是叫做匿名用户的字符串
        /*if (!(principal instanceof UserDetails)){
            response.sendRedirect( request.getContextPath() + "/login.html");
        }else {*/
            //如果判断是 AJAX 请求,直接设置为session超时
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with")
                                                                        .equals("XMLHttpRequest"))
            {
                response.setHeader("sessionstatus", "timeout");
                response.getWriter().write("123");
            } else
            {
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
//        }
    }
}
