package com.feign.clients;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘太全
 * @program springCloud
 * @date 2019-05-23 14:09
 **/
@Configuration
public class FeignConfiguration {

    @Bean
    @ConditionalOnProperty(value = {
            "feign.client.config.feign-service.username",
            "feign.client.config.feign-service.password"})
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${feign.client.config.feign-service.username}") String username,
            @Value("${feign.client.config.feign-service.password}") String password){
        return new BasicAuthRequestInterceptor(username,password);
    }
}
