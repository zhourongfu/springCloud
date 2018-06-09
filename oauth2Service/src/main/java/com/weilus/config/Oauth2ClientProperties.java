package com.weilus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;

@Configuration
@RefreshScope
@ConfigurationProperties("uaa.oauth2")
public class Oauth2ClientProperties {

    /**
     * 认证服务器 秘钥相关配置
     */
    private Oauth2Client[] clients;

    public Oauth2Client[] getClients() {
        return clients;
    }

    public void setClients(Oauth2Client[] clients) {
        this.clients = clients;
    }

    public static class Oauth2Client extends BaseClientDetails{
        private String roles;

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
            if(StringUtils.hasText(roles)){
                Set<String> role_set = StringUtils.commaDelimitedListToSet(roles);
                List<GrantedAuthority> authorities=new ArrayList<>();
                role_set.forEach((s)->{authorities.add(new SimpleGrantedAuthority(s));});
                super.setAuthorities(authorities);
            }
        }
    }
}
