package com.weilus.serviceCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by liutq on 2018/5/25.
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled = true)
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    public static final String UAA_SERVICE_ID="uaa";
    static String[] NOT_CHECK_TOKEN_PATTERNS =new String[]{
            "/test",
            "/timeout",
            "/uaa/oauth/authorize",
            "/uaa/oauth/token",
            "/uaa/login",
            "/favicon.ico"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers(NOT_CHECK_TOKEN_PATTERNS).permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated();
    }

    @Configuration
    @EnableEurekaClient
    @RibbonClient(name = UAA_SERVICE_ID)
    public static class CheckTokenConfigurer{
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
            services.setCheckTokenEndpointUrl("http://"+UAA_SERVICE_ID+"/oauth/check_token");
            services.setRestTemplate(restTemplate);
            return services;
        }
    }


}
