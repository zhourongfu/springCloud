package com.weilus;

import com.weilus.service.Auth2ClientService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessTokenFilter implements Filter {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);

    private ResourceServerTokenServices tokenServices;

    public AccessTokenFilter(RedisConnectionFactory connectionFactory,Auth2ClientService auth2ClientService){
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(redisTokenStore);
        defaultTokenServices.setClientDetailsService(auth2ClientService);
        tokenServices=defaultTokenServices;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String accessToken = req.getParameter("access_token");
        if(!StringUtils.isEmpty(accessToken)){
            logger.info("AccessTokenFilter get token ====>"+accessToken);
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = tokenServices.loadAuthentication(accessToken);
            ctx.setAuthentication(auth);
        }
        chain.doFilter(req,resp);
    }
    @Override
    public void destroy() {
    }
}
