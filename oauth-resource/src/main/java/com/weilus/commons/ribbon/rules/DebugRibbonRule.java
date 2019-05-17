package com.weilus.commons.ribbon.rules;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Debug优先调用负载策略 application.yml加上如下配置
 * serviceProducer: #微服务serviceProducer 负载均衡策略(灰度发布策略)
 *  ribbon:
 *#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.ZoneAvoidanceRule  默认策略
 *#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule     随机策略
 *     NFLoadBalancerRuleClassName: com.weilus.serviceCustomer.GrayRibbonRule   灰度策略
 *l
 * #Hystrix隔离策略=信号量隔离
 * hystrix:
 *  command:
 *   default:
 *    execution:
 *     isolation:
 *      strategy: SEMAPHORE
 * Created by liutq on 2018/6/15.
 */
public class DebugRibbonRule extends ClientConfigEnabledRoundRobinRule {
    public static final Logger LOGGER = LoggerFactory.getLogger(DebugRibbonRule.class);
    public static final String META_DEBUG_KEY = "debug";

    private AbstractServerPredicate predicate = new AbstractServerPredicate(){
        @Override
        public boolean apply(PredicateKey input) {
            if(input.getServer() instanceof DiscoveryEnabledServer){
                InstanceInfo info = ((DiscoveryEnabledServer)input.getServer()).getInstanceInfo();
                Map metadata = info.getMetadata();
                LOGGER.info("{} metadata : {}",info.getInstanceId(),metadata);
                if(isDebugRequest()){//debug 调用
                    //选择微服务带元信息[debug]Server 排除正常Server
                    return metadata.containsKey(META_DEBUG_KEY) && "true".equals(metadata.get(META_DEBUG_KEY));
                }else{//正常请求链路
                    if(metadata.containsKey(META_DEBUG_KEY))return false;//排除微服务带元信息[debug]的 Server
                    else return true;//选择正常Server
                }
            }
            return  false;
        }
    };

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = this.getLoadBalancer();
        com.google.common.base.Optional<Server> op = predicate.chooseRandomlyAfterFiltering(lb.getAllServers(),key);
        if(op.isPresent())
        {
            return op.get();
        }
        else if(isDebugRequest())
        {
            op = AbstractServerPredicate.alwaysTrue().chooseRandomlyAfterFiltering(lb.getAllServers(),key);
            return op.isPresent() ? op.get() : null;
        }
        return null;
    }


    private boolean isDebugRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String debug = request.getHeader(META_DEBUG_KEY);
        return !StringUtils.isEmpty(debug);
    }
}
