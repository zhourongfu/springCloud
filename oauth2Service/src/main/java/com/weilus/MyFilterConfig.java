package com.weilus;

import com.weilus.service.Auth2ClientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
public class MyFilterConfig{

    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private Auth2ClientService auth2ClientService;

    @Bean
    public FilterRegistrationBean userFilter() {
        FilterRegistrationBean myFilter = new FilterRegistrationBean();
        myFilter.addUrlPatterns("/*");
        myFilter.setOrder(1);
        myFilter.setName("x-auth-id-filter");
        myFilter.setFilter(new AccessTokenFilter(connectionFactory,auth2ClientService));
        return myFilter;
    }
}
