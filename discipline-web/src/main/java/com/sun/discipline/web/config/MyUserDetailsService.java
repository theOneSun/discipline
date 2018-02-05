package com.sun.discipline.web.config;

import com.sun.discipline.dao.mybatis.UserMapper;
import com.sun.discipline.domain.common.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Created by sj on 2018/2/5.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public MyUserDetailsService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByCode(username);
        String password = passwordEncoder.encode(user.getPassword());
        logger.info("登录的用户是: " + username + "encode 后的密码是: " + password);
        return new org.springframework.security.core.userdetails.User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
