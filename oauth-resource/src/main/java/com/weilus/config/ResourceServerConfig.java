package com.weilus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Created by liutq on 2018/12/11.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    @Autowired
    SecurityMetadataSourcePropertity propertity;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        FilterSecurityPostProcessor processor = new FilterSecurityPostProcessor();
        processor.setPropertity(propertity);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        if(!CollectionUtils.isEmpty(propertity.getNoCheckToken())) {
            propertity.getNoCheckToken().forEach(s -> {
                HttpMethod httpmethod = null;
                String pattern = s;
                if (s.indexOf(" ") != -1) {
                    String[] arr = StringUtils.delimitedListToStringArray(s, " ");
                    httpmethod = StringUtils.hasText(arr[0]) ? HttpMethod.valueOf(arr[0]) : null;
                    pattern = arr[1];
                }
                registry.antMatchers(httpmethod, pattern).permitAll();
            });
        }
        registry.anyRequest().authenticated().withObjectPostProcessor(processor);
    }
}
