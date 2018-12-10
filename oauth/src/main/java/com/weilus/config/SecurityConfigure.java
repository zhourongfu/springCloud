package com.weilus.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Created by liutq on 2018/10/23.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled = true,proxyTargetClass=true)
@EnableResourceServer
public class SecurityConfigure extends ResourceServerConfigurerAdapter {
    public static final Logger logger = LoggerFactory.getLogger(ResourceServerConfigurer.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisConnectionFactory connectionFactory;
    @Autowired
    ClientDetailsService clientDetailsService;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new RedisTokenStore(connectionFactory));
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }
    @Bean
    public ClientDetailsService clientDetailsService(@Autowired DataSource dataSource){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired DataSource dataSource){
        JdbcDaoImpl dao =new JdbcDaoImpl();
        dao.setDataSource(dataSource);
        dao.setEnableGroups(true);
        dao.setRolePrefix("ROLE_");
        return dao;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //所有注解 @CheckLogin(false) 不验证登录
                .authorizeRequests().antMatchers("/oauth/token").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(outh2AuthenticationProcessingFilter(),AbstractPreAuthenticatedProcessingFilter.class)
//                .authenticationProvider(authenticationProvider())
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

    @Bean
    public OAuth2AuthenticationProcessingFilter outh2AuthenticationProcessingFilter(){
        OAuth2AuthenticationProcessingFilter f = new OAuth2AuthenticationProcessingFilter();
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(new DefaultWebResponseExceptionTranslator(){
            @Override
            public ResponseEntity translate(Exception e) throws Exception {
                return new ResponseEntity(Oauth2Error.INVALID_TOKEN, HttpStatus.OK);
            }
        });
        f.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);
        OAuth2AuthenticationManager o = new OAuth2AuthenticationManager();
        o.setTokenServices(tokenServices());
        f.setAuthenticationManager(o);
        return f;
    }

    enum Oauth2Error{
        INVALID_TOKEN("APP_INVALID_TOKEN","无效的token"),
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