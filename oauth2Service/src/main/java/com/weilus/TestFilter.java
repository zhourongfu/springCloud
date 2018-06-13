package com.weilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by liutq on 2018/6/12.
 */
@Configuration
public class TestFilter implements Filter{
    private static Logger logger = LoggerFactory.getLogger(TestFilter.class);


    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TestFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        System.err.println("HEADERS  REPORTS=============");
        Enumeration<String> e= req.getHeaderNames();
        while(e.hasMoreElements()){
            String headerName = e.nextElement();
            logger.info(headerName+" = "+req.getHeader(headerName));
        }
        System.err.println("PARAMETERS  REPORTS=============");
        Enumeration<String> p = req.getParameterNames();
        while(p.hasMoreElements()){
            String paramName = p.nextElement();
            logger.info(paramName+" = "+req.getParameter(paramName));
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
