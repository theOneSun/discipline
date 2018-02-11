package com.sun.discipline.web.authentication;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

/**
 * @author sunjian.
 */
@Component("disciplineAuthenticationSuccessHandler")
public class DisciplineAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    @Autowired
    public DisciplineAuthenticationSuccessHandler(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        logger.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message",objectMapper.writeValueAsString(authentication));
        response.getWriter().write(JSON.toJSONString(map));
//        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
