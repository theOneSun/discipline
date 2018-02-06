package com.sun.discipline.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * springSecurity配置类
 *
 * @author sunjian.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final SecurityProperties securityProperties;
    private final AuthenticationSuccessHandler disciplineAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler disciplineAuthenticationFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SecurityConfig(SecurityProperties securityProperties,
                          AuthenticationSuccessHandler disciplineAuthenticationSuccessHandler,
                          AuthenticationFailureHandler disciplineAuthenticationFailureHandler)
    {
        this.securityProperties = securityProperties;
        this.disciplineAuthenticationSuccessHandler = disciplineAuthenticationSuccessHandler;
        this.disciplineAuthenticationFailureHandler = disciplineAuthenticationFailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf()
            .disable()//禁用csrf保护
            .authorizeRequests()
            .antMatchers("/public/**",
                    "/login/**",
                    "/*.html",// static下的html全部放行
                    securityProperties.getBrowserProperties().getLoginPage())
            .permitAll()
            .antMatchers("/demo/**")
            .hasAnyAuthority("admin")
//            .hasRole("admin") // 权限或是角色貌似只能选择一个做配置
            .anyRequest()//所有请求都需要
            .authenticated()//以上三个方法的作用:确保我们应用中的所有请求都需要用户被认证
            .and()//java配置使用and()方法相当于XML标签的关闭。 这样允许我们继续配置父类节点
            .formLogin()//表单登录
//            .loginPage("/public/login")
            .loginProcessingUrl("/public/login")
            .successHandler(disciplineAuthenticationSuccessHandler)
//            .failureHandler(disciplineAuthenticationFailureHandler)
//            .successForwardUrl("/demo/loginSuccess")
            .usernameParameter("code")//设置登录的账号的key是code
//            .passwordParameter("123")//设置登录提交的密码的key是123
            .permitAll()//允许所有人登录
            .and()
            .logout()
//            .logoutUrl("/public/logout")
            .logoutSuccessUrl("/public/logout")
            //.logoutSuccessHandler(logoutSuccessHandler)
            .invalidateHttpSession(true)
//            .addLogoutHandler(logoutHandler)
            .deleteCookies("JSESSIONID")
            .and();
    }


    //权限添加,授权登录配置
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication()
//            .withUser("admin")
//            .password("123")
//            .roles("admin")
//            .and()
//            .withUser("user")
//            .password("123")
//            .roles("user");
//    }
}
