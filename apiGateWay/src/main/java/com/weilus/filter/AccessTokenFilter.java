package com.weilus.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AccessTokenFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a != null){
            System.err.println(a);
            System.err.println("==============>"+a.getName()+"<================");
            ctx.addZuulRequestHeader("X-AUTH-ID",a.getPrincipal().toString());
        }
        return null;
    }
}
