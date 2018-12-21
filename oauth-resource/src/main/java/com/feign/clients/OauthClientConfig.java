package com.feign.clients;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OauthClientConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("acme","acmesecret");
    }
    @Bean
    Request.Options feignRequestOptions(){
        return new Request.Options(10 * 1000, 7 * 1000);
    }
//    @Bean
//    public Decoder decoder(){
//        return new Decoder.Default();
//    }
//    @Bean
//    public ErrorDecoder feignDecoder() {
//        return new ErrorDecoder.Default();
//    }
//
//    @Bean
//    public Feign.Builder builder(){
//        return Feign.builder();
//    }

}
