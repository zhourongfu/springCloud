package com.weilus.commons.ribbon.rules;

import com.netflix.appinfo.InstanceInfo;
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
 * Debug优先调用负载策略 application.yml加上如下配置
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
public class DebugRibbonRule extends PredicateBasedRule {

    public static final String META_DEBUG_KEY = "debug";

    private AbstractServerPredicate metadataAwarePredicate = new AbstractServerPredicate(){
        public boolean apply(PredicateKey input) {
            if(input != null && input.getServer() instanceof DiscoveryEnabledServer){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String debug = request.getHeader(META_DEBUG_KEY);
                Set attributes = StringUtils.isEmpty(debug) ? null : Collections.singletonMap(META_DEBUG_KEY,debug).entrySet();
                InstanceInfo info = ((DiscoveryEnabledServer)input.getServer()).getInstanceInfo();
                Map metadata = info.getMetadata();
                if(attributes == null){//正常请求链路
                    if(metadata.containsKey(META_DEBUG_KEY))return false;//排除微服务带元信息[debug]的 Server
                    else return true;//选择正常Server
                }else {//debug 调用
                    //选择微服务带元信息[debug]Server 排除正常Server
                    return metadata.entrySet().containsAll(attributes);
                }
            }
            return  false;
        }
    };

    public AbstractServerPredicate getPredicate() {
        return CompositePredicate.withPredicates(metadataAwarePredicate,new AvailabilityPredicate(this,null)).build();
    }
}
