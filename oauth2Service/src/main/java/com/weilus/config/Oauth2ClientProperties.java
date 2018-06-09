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
     * 密码模式 认证服务配置
     */
    private Oauth2Client passwordClient;
    /**
     * 授权码模式 认证服务配置
     */
    private Oauth2Client authorizationClient;
    /**
     * 客户端模式 认证服务配置
     */
    private Oauth2Client credentialsClient;


    public Oauth2Client getPasswordClient() {
        return passwordClient;
    }

    public void setPasswordClient(Oauth2Client passwordClient) {
        this.passwordClient = passwordClient;
    }

    public Oauth2Client getAuthorizationClient() {
        return authorizationClient;
    }

    public void setAuthorizationClient(Oauth2Client authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public Oauth2Client getCredentialsClient() {
        return credentialsClient;
    }

    public void setCredentialsClient(Oauth2Client credentialsClient) {
        this.credentialsClient = credentialsClient;
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
