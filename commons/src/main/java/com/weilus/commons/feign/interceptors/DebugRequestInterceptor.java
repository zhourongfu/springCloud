package com.weilus.commons.feign.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * FEIGN 传递参数到下一个服务
 * Created by liutq on 2019/3/15.
 */
public class DebugRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String debug = request.getHeader("debug");
        if(!StringUtils.isEmpty(debug))template.header("debug",debug);
    }
}
