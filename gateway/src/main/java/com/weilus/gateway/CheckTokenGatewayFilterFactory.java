package com.weilus.gateway;

import com.feign.clients.OauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author liutaiq
 * @program springCloud
 * @date 2019-05-31 09:40
 **/
@Component
@ConditionalOnBean(OauthClient.class)
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.CheckTokenConfig> {

    @Autowired
    OauthClient oauthClient;

    public CheckTokenGatewayFilterFactory(){
        super(CheckTokenConfig.class);
    }

    @Override
    public GatewayFilter apply(CheckTokenConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            Map<String,String> map = oauthClient.checkToken(token);
            if (map.containsKey("error")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }else {
                System.out.println(map);
                Set<String> scope = extractScope(map);
                String serviceId = StringUtils.tokenizeToStringArray(request.getURI().getRawPath(), "/")[0];
                if(scope.contains(serviceId)){
                    return chain.filter(exchange);
                }else {
                    ServerHttpResponse response = exchange.getResponse();
                    HttpHeaders httpHeaders = response.getHeaders();
                    httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
                    //设置body
                    String body = String.format("不能访问服务%s",serviceId);
                    DataBuffer bodyBuffer = response.bufferFactory().wrap(body.getBytes());
                    return response.writeWith(Mono.just(bodyBuffer));
                }
            }
        };
    }

    public static class CheckTokenConfig{

    }


    private Set<String> extractScope(Map<String, ?> map) {
        Set<String> scope = Collections.emptySet();
        if (map.containsKey("scope")) {
            Object scopeObj = map.get("scope");
            if (String.class.isInstance(scopeObj)) {
                scope = new LinkedHashSet<String>(Arrays.asList(String.class.cast(scopeObj).split(" ")));
            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
                @SuppressWarnings("unchecked")
                Collection<String> scopeColl = (Collection<String>) scopeObj;
                scope = new LinkedHashSet<String>(scopeColl);	// Preserve ordering
            }
        }
        return scope;
    }
}
