package com.weilus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created by liutq on 2018/5/22.
 */
@Configuration
@EnableResourceServer
public class RSSConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
    }
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    public void configureGlobal(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints.tokenStore(new RedisTokenStore(connectionFactory));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/user").authorizeRequests().anyRequest().authenticated();
    }

}
