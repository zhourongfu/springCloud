package com.weilus;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * ribbon调用 灰度发布的服务
 * Created by liutq on 2018/6/14.
 */
@Configuration
public class GrayDeployConfigurer {
    public static final String HEADER = "gray";
    public static final ThreadLocal<Set> CURRENT_GRAY = new InheritableThreadLocal<>();
    @Bean
    public GrayRule grayRule(){
        return new GrayRule();
    }
    @Bean
    public ZuulFilter zuulFilter(){
        return new ZuulFilter() {
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
                String reqGray = request.getHeader(HEADER);
                if(!StringUtils.isEmpty(reqGray)){
                    Set gray = Collections.singletonMap(HEADER,reqGray).entrySet();
                    CURRENT_GRAY.set(gray);
                }else{
                    CURRENT_GRAY.set(null);
                }
                return null;
            }
        };
    }

    public static class GrayRule extends PredicateBasedRule{

        private AbstractServerPredicate metadataAwarePredicate = new AbstractServerPredicate(){
            @Override
            public boolean apply(PredicateKey input) {
                if(input != null && input.getServer() instanceof DiscoveryEnabledServer){
                    Set attributes = CURRENT_GRAY.get();
                    Map metadata = ((DiscoveryEnabledServer)input.getServer()).getInstanceInfo().getMetadata();
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
