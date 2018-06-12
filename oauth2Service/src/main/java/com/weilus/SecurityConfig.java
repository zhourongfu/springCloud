package com.weilus;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
授权码模式
 http://192.168.100.3:8080/oauth/authorize?client_id=a10086&response_type=code&scope=user_info&redirect_url=http://aa.ccdd

 curl -d 'grant_type=authorization_code&client_id=a10086&scope=user_info&redirect_uri=http://aa.ccdd&code=pg4Vz2'  \
 http://a10086:aabcc@192.168.100.3:8080/oauth/token
**/

/**
密码模式
 curl -d 'grant_type=password&client_id=acme&username=admin&password=weilus' \
 http://acme:acmesecret@192.168.100.3:8080/oauth/token
**/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().mvcMatchers("/oauth/authorize").authenticated()
                .and()
                .formLogin()
                .loginPage("/uaa/login")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler("uaa"))
                .loginProcessingUrl("/login");//zuul代理
    }


}