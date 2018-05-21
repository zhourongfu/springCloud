package com.weilus;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by liutq on 2018/5/21.
 */
//@Configuration
//@EnableResourceServer
public class RSSConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/me")
                    .authorizeRequests().anyRequest().authenticated();
        }
}
