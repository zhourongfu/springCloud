package com.weilus.serviceCustomer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * feign调用 灰度发布的服务
 * Created by liutq on 2018/6/14.
 */
@Configuration
public class GrayDeployConfigurer {
    public static final String HEADER = "gray";
    public static final ThreadLocal<Set> CURRENT_GRAY = new InheritableThreadLocal<>();

//    @Bean
//    public ZoneAvoidanceRule defaultRule(){
//        return new ZoneAvoidanceRule();
//    }
    @Bean
    public GrayRule grayRule(){
        return new GrayRule();
    }

    @Bean
    public FilterRegistrationBean filter(){
        FilterRegistrationBean reg = new FilterRegistrationBean();
        reg.setFilter(new GenericFilterBean() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest)request;
                String reqGray = req.getHeader(HEADER);
                if(!StringUtils.isEmpty(reqGray)){
                    Set gray = Collections.singletonMap(HEADER,reqGray).entrySet();
                    CURRENT_GRAY.set(gray);
                }else{
                    CURRENT_GRAY.set(null);
                }
                chain.doFilter(request,response);
            }
        });
        reg.addUrlPatterns("/*");
        return reg;
    }

    public static class GrayRule extends PredicateBasedRule{
        private AbstractServerPredicate metadataAwarePredicate = new AbstractServerPredicate(){

            @Override
            public boolean apply(PredicateKey input) {
                if(input != null && input.getServer() instanceof DiscoveryEnabledServer){
                    Set attributes = CURRENT_GRAY.get();
                    InstanceInfo info = ((DiscoveryEnabledServer)input.getServer()).getInstanceInfo();
                    Map metadata = info.getMetadata();
                    return metadata.entrySet().containsAll(attributes);
                }
                return  false;
            }
        };

        public AbstractServerPredicate getPredicate() {
            return CompositePredicate.withPredicates(metadataAwarePredicate,new AvailabilityPredicate(this, (IClientConfig)null)).build();
        }
    }
}
