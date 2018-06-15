package com.weilus;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 灰度发布 负载策略 application.yml加上如下配置
 * serviceProducer: #微服务serviceProducer 负载均衡策略(灰度发布策略)
 *  ribbon:
 *#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.ZoneAvoidanceRule  默认策略
 *#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule     随机策略
 *     NFLoadBalancerRuleClassName: com.weilus.serviceCustomer.GrayRibbonRule   灰度策略
 *
 * #Hystrix隔离策略=信号量隔离
 * hystrix:
 *  command:
 *   default:
 *    execution:
 *     isolation:
 *      strategy: SEMAPHORE
 * Created by liutq on 2018/6/15.
 */
public class GrayRibbonRule extends PredicateBasedRule {

    public static final String META_GRAY_KEY = "gray";

    private AbstractServerPredicate metadataAwarePredicate = new AbstractServerPredicate(){
        @Override
        public boolean apply(PredicateKey input) {
            if(input != null && input.getServer() instanceof DiscoveryEnabledServer){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String reqGray = request.getHeader(META_GRAY_KEY);
                Set attributes = StringUtils.isEmpty(reqGray) ? null : Collections.singletonMap(META_GRAY_KEY,reqGray).entrySet();
                InstanceInfo info = ((DiscoveryEnabledServer)input.getServer()).getInstanceInfo();
                Map metadata = info.getMetadata();
                if(attributes == null){//正常请求链路
                    if(metadata.containsKey(META_GRAY_KEY))return false;//排除灰度Server
                    else return true;//选择正常Server
                }else {//灰度调用
                    //选择灰度Server 排除正常Server
                    return metadata.entrySet().containsAll(attributes);
                }
            }
            return  false;
        }
    };

    public AbstractServerPredicate getPredicate() {
        return CompositePredicate.withPredicates(metadataAwarePredicate,new AvailabilityPredicate(this, (IClientConfig)null)).build();
    }
}
