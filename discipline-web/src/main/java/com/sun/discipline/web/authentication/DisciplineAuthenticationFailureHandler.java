package com.sun.discipline.web.authentication;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.discipline.domain.common.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author sunjian.
 */
@Component("disciplineAuthenticationFailureHandler")
public class DisciplineAuthenticationFailureHandler implements AuthenticationFailureHandler
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    @Autowired
    public DisciplineAuthenticationFailureHandler(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException
    {
        logger.info("登录失败");
//        throw new RuntimeException("密码错误");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        //        HashMap<String, Object> map = new HashMap<>();
//        map.put("success",false);
//        map.put("errorCode",5641321);
//        map.put("message",objectMapper.writeValueAsString(exception.getMessage()));
        response.getWriter().write(JSON.toJSONString(JsonResponse.failureJsonResponse("13465","登录失败")));
//        response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
    }
}
