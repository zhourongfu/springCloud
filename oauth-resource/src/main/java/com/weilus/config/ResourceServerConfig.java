package com.weilus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by liutq on 2018/12/11.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Configuration
    @EnableEurekaClient
    @RibbonClient(name = "${oauth.check-token.service-id}")
    @AutoConfigureAfter(AuthorizationServerProperties.class)
    public static class CheckTokenConfigurer{
        @Autowired
        AuthorizationServerProperties properties;

        @LoadBalanced
        @Bean("remoteTokenRestTemplate")
        public RestTemplate restTemplate(){
            RestTemplate restTemplate = new RestTemplate();
            ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(ClientHttpResponse response) throws IOException {
                    if (response.getRawStatusCode() != 400) {
                        super.handleError(response);
                    }
                }
            });
            return restTemplate;
        }

        @Bean
        @Primary
        @Autowired
        @Qualifier(value = "remoteTokenRestTemplate")
        public RemoteTokenServices remoteTokenServices(RestTemplate restTemplate){
            RemoteTokenServices services = new RemoteTokenServices();
            services.setCheckTokenEndpointUrl("http://"+properties.getServiceId()+"/oauth/check_token");
            services.setRestTemplate(restTemplate);
            services.setClientId(properties.getClientId());
            services.setClientSecret(properties.getClientSecret());
            return services;
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }
}
