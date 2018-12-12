package com.weilus.hystrix;

import com.feign.FeignClientService;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Configuration
public class FeignConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("fadffsedfsf","rrfdaDSFfesnmhf");
    }
    @Bean
    Request.Options feignRequestOptions(){
        return new Request.Options(10 * 1000, 7 * 1000);
    }

    @Component
    public static class CallBack implements FeignClientService {
        @Override
        public String sayHello(@RequestBody Map<String, String> map) {
            return "feign-call: Hello "+map.get("name");
        }

        @Override
        public String hiMan(@RequestBody Map<String, String> map) {
            return "feign-call: Hi "+map.get("name");
        }
    }
}
