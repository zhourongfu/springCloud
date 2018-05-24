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
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        endpoints.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Enumeration<String> e = request.getHeaderNames();
                Map<String,String> headers = new HashMap<>();
                while (e.hasMoreElements()){
                    String headerName = e.nextElement();
                    headers.put(headerName,request.getHeader(headerName));
                }
                logger.info(headers.toString());
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                Enumeration<String> e = request.getHeaderNames();
                Map<String,String> headers = new HashMap<>();
                while (e.hasMoreElements()){
                    String headerName = e.nextElement();
                    headers.put(headerName,request.getHeader(headerName));
                }
                logger.info(headers.toString());
            }
        });
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(auth2ClientService);
    }

}
