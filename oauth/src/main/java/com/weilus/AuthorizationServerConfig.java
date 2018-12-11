package com.weilus;

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
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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
    private DefaultTokenServices tokenServices;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new RedisTokenStore(connectionFactory));
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.exceptionTranslator((e) -> {
            return new ResponseEntity<OAuth2Exception>(new OAuth2Exception("用户名或密码错误",e),HttpStatus.OK);
        });
        endpoints.tokenStore(new RedisTokenStore(connectionFactory));
        endpoints.tokenServices(tokenServices);
//        endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("password","refresh_token")
                .scopes("user_info")
                .authorities("USER_ADD")
                .refreshTokenValiditySeconds(7200).accessTokenValiditySeconds(3600)
                .and()
                .withClient("a10086")
                .secret("aabcc")
                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("user_info","base_info")
                .refreshTokenValiditySeconds(7200).accessTokenValiditySeconds(3600)
                .redirectUris("http://aa.ccdd").authorities("AUTHORI_CLIENT_ADD")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }

}
