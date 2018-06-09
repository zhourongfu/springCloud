package com.weilus.service;

import com.weilus.config.Oauth2ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by liutq on 2018/5/23.
 */
@Service
public class Auth2ClientService implements ClientDetailsService {

//    static Set<ClientDetails> clients = new HashSet<>();
//    static {
//        //密码模式
//        BaseClientDetails user_client = new BaseClientDetails();
//        user_client.setClientId("acme");
//        user_client.setClientSecret("acmesecret");
//        user_client.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet("password,refresh_token"));
//        user_client.setScope(StringUtils.commaDelimitedListToSet("user_info"));
//        List<GrantedAuthority> user_client_roles = new ArrayList<>();
//        user_client_roles.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return ("USER_ADD");
//            }
//        });
//        user_client.setAuthorities(user_client_roles);
//        user_client.setAccessTokenValiditySeconds(3600);
//        user_client.setRefreshTokenValiditySeconds(7200);
//        clients.add(user_client);
//
//        //授权码模式
//        BaseClientDetails authorization_code_client = new BaseClientDetails();
//        authorization_code_client.setClientId("a10086");
//        authorization_code_client.setClientSecret("aabcc");
//        authorization_code_client.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet("authorization_code,refresh_token"));
//        authorization_code_client.setScope(StringUtils.commaDelimitedListToSet("user_info,base_info"));
//        List<GrantedAuthority> authorization_code_roles = new ArrayList<>();
//        authorization_code_roles.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return ("AUTHORI_CLIENT_ADD");
//            }
//        });
//        authorization_code_client.setAuthorities(authorization_code_roles);
//        authorization_code_client.setAccessTokenValiditySeconds(3600);
//        authorization_code_client.setRefreshTokenValiditySeconds(7200);
//        authorization_code_client.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet("http://aa.ccdd"));
//        authorization_code_client.setAutoApproveScopes(StringUtils.commaDelimitedListToSet("base_info"));//指定需要用户 确定授权
//        clients.add(authorization_code_client);
//
//        //客户端模式
//        BaseClientDetails client_credentials = new BaseClientDetails();
//        client_credentials.setClientId("ccdit");
//        client_credentials.setClientSecret("ccditsec");
//        client_credentials.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet("client_credentials,refresh_token"));
//        client_credentials.setScope(StringUtils.commaDelimitedListToSet("read,write"));
//        List<GrantedAuthority> client_credentials_roles = new ArrayList<>();
//        authorization_code_roles.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return ("CLIENT_CREDENTIALS_ADD");
//            }
//        });
//        client_credentials.setAuthorities(authorization_code_roles);
//        client_credentials.setAccessTokenValiditySeconds(3600);
//        client_credentials.setRefreshTokenValiditySeconds(7200);
//        clients.add(client_credentials);
//
//    }
//    public static ClientDetails getById(String clientId){
//        for (ClientDetails c:clients) {
//            if(c.getClientId().equals(clientId)){
//                return c;
//            }
//        }
//        return null;
//    }

    @Autowired
    private Oauth2ClientProperties properties;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails result=null;
        if(properties.getAuthorizationClient().getClientId().equals(clientId))
            result = properties.getAuthorizationClient();
        else if(properties.getPasswordClient().getClientId().equals(clientId))
            result = properties.getPasswordClient();
        else if(properties.getCredentialsClient().getClientId().equals(clientId))
            result = properties.getCredentialsClient();
        return result;

//        return getById(clientId);
    }

}
