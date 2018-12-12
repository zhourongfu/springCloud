package com.feign.clients;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
