package com.weilus;

import com.weilus.service.Auth2ClientService;
import com.weilus.service.Auth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
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
    private Auth2UserService auth2UserService;
    @Autowired
    private Auth2ClientService auth2ClientService;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(auth2UserService);
        endpoints.tokenStore(redisTokenStore);
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore);
        tokenServices.setClientDetailsService(auth2ClientService);
        tokenServices.setAuthenticationManager(authenticationManager);
        endpoints.tokenServices(tokenServices);

//        endpoints.authenticationManager(authenticationManager);
//        endpoints.userDetailsService(auth2UserService);
//        endpoints.accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(auth2ClientService);
    }
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
//    @Primary
//    @Bean("jwtAccessTokenConverter")
//    public JwtAccessTokenConverter jwtAccessTokenConverter(){
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter(){
//            @Override
//            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                String username = authentication.getUserAuthentication().getName();
//                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(Collections.singletonMap("username",username));
//                return super.enhance(accessToken, authentication);
//            }
//        };
//        //java keytool工具生成keystore文件:
//        //keytool -genkey -alias admin -keysize 2048 -keyalg RSA -keystore D:\\test.jks
//        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("test.jks"),"weilus".toCharArray()).getKeyPair("admin");
//        //String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
//        jwtAccessTokenConverter.setKeyPair(keyPair);
//        return jwtAccessTokenConverter;
//    }
}
