package com.sun.discipline.web.service.impl;

import com.sun.discipline.dao.mybatis.UserMapper;
import com.sun.discipline.domain.common.User;
import com.sun.discipline.domain.common.WechatUser;
import com.sun.discipline.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author sunjian.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    @Override
    public boolean signUp(String username,String password)
    {
        // todo 注册用户
        /*
        1.先查询username(手机号)是否可用
        2.判断当前微信是否绑定过手机号
         */
        User userByMobile = userMapper.getByMobile(username);
        if (!ObjectUtils.isEmpty(userByMobile)){
            return false;
        }
        WechatUser byOpenId = userMapper.getByOpenId("2123");
        if (ObjectUtils.isEmpty(byOpenId)){
            return false;
        }else {
            if (!StringUtils.isBlank(byOpenId.getUserId())){
                //不为空,微信已绑定过用户
                return false;
            }
        }

        // todo 执行注册绑定微信


        return false;
    }

}
