package com.weilus;

import com.alibaba.druid.pool.DruidDataSource;
import com.weilus.service.Auth2ClientService;
import com.weilus.service.Auth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * Created by liutq on 2018/5/23.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private Auth2UserService auth2UserService;
    @Autowired
    private Auth2ClientService auth2ClientService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DefaultTokenServices tokenServices;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new RedisTokenStore(connectionFactory));
        tokenServices.setClientDetailsService(auth2ClientService);
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(auth2UserService);
        endpoints.exceptionTranslator((e) -> {
            return new ResponseEntity<OAuth2Exception>(new OAuth2Exception("用户名或密码错误",e),HttpStatus.OK);
        });
        endpoints.tokenStore(new RedisTokenStore(connectionFactory));
        endpoints.tokenServices(tokenServices);
        endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(auth2ClientService);
    }

}
