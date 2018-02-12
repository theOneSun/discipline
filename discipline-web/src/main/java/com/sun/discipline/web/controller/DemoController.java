package com.sun.discipline.web.controller;

import com.sun.discipline.dao.mybatis.UserMapper;
import com.sun.discipline.domain.common.User;
import com.sun.discipline.utils.math.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjian.
 */
@RestController
@RequestMapping("/demo")
public class DemoController
{
    private UserMapper userMapper;

    @Autowired
    public DemoController(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello World!      "+ UUIDUtils.getUUID();
    }

    @RequestMapping("/login")
    public boolean login(@RequestParam("code") String code,@RequestParam("password") String password){
        User user = userMapper.getByCodeAndPassword(code, password);

        return !ObjectUtils.isEmpty(user);
    }
    @RequestMapping("/loginSuccess")
    public String loginSuccess(){
       return "登录成功!";
    }

    @RequestMapping("/info")
    public String currentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.toString();
    }
}
