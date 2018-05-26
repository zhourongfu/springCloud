package com.weilus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@EnableEurekaClient
@RibbonClient(name = "uaa")
@Configuration
public class ToeknConfig {

    @LoadBalanced
    @Bean("remoteTokenRestTemplate")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }

    @Primary
    @Bean
    @Autowired
    @Qualifier("remoteTokenRestTemplate")
    public RemoteTokenServices remoteTokenServices(RestTemplate restTemplate){
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://uaa/oauth/check_token");
        services.setRestTemplate(restTemplate);
        return services;
    }

}
