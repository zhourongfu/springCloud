package com.weilus.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liutq on 2018/10/23.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled = true,proxyTargetClass=true)
@EnableResourceServer
public class SecurityConfigure extends ResourceServerConfigurerAdapter {
    public static final Logger logger = LoggerFactory.getLogger(ResourceServerConfigurer.class);

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().mvcMatchers("/oauth/authorize").authenticated()
                .and()
                .formLogin().loginPage("/login").passwordParameter("password").usernameParameter("username")
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)->{
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        new ObjectMapper().writeValue(httpServletResponse.getWriter(),Oauth2Error.ACCESS_DENIED);
                })
                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException e)->{
                        response.setContentType("application/json;charset=UTF-8");
                        new ObjectMapper().writeValue(response.getWriter(),Oauth2Error.UNAUTHORIZED);
                });

    }

    enum Oauth2Error{
        UNAUTHORIZED("APP_UNAUTHORIZED","未登录"),
        ACCESS_DENIED("APP_ACCESS_DENIED","无权限访问");
        String code;
        String msg;
        Oauth2Error(String code,String msg){
            this.code = code;
            this.msg = msg;
        }
    }

}