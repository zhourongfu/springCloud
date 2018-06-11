package com.weilus.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

//@Component
public class GrayDeployFilter extends ZuulFilter {
    public static final String GRAY = "gray";
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String gray = request.getHeader(GRAY);
        System.err.println(GRAY+"===>"+gray);
        if ("dev".equals(gray)) {
            RibbonFilterContextHolder.getCurrentContext().add(GRAY, "dev");
        }
        else {
            RibbonFilterContextHolder.getCurrentContext().add(GRAY, "test");
        }
        return null;
    }
}
