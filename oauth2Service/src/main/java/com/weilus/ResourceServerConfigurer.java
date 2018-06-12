package com.weilus;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by liutq on 2018/5/25.
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled = true)
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    static String[] NOT_CHECK_TOKEN_PATTERNS =new String[]{
//            "/serviceCustomer/**",
            "/uaa/oauth/authorize",
            "/uaa/oauth/token",
            "/uaa/login",
            "/favicon.ico"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers(NOT_CHECK_TOKEN_PATTERNS).permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated();
    }

}
