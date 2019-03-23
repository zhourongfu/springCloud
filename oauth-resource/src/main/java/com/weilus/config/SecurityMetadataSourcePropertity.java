package com.weilus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liutq on 2019/3/23.
 */
@Configuration
@ConfigurationProperties(prefix = "security.metadata-source")
public class SecurityMetadataSourcePropertity {

    private Map<String,String> rules;

    public Map<String,String> getRules() {
        return rules;
    }

    public void setRules(Map<String,String> rules) {
        this.rules = rules;
    }

    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getMetaSource(){
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap();
        if(!CollectionUtils.isEmpty(rules)){
            rules.entrySet().forEach(e->{
                String[] arr = StringUtils.delimitedListToStringArray(e.getKey()," ");
                requestMap.put(new AntPathRequestMatcher(arr[1],arr[0]),
                        SecurityConfig.createListFromCommaDelimitedString(e.getValue()));
            });
        }
        return requestMap;
    }
}
