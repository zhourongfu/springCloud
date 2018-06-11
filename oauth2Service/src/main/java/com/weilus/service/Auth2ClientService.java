package com.weilus.service;

import com.weilus.config.Oauth2ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * Created by liutq on 2018/5/23.
 */
@Service
public class Auth2ClientService implements ClientDetailsService {
    @Autowired
    private Oauth2ClientProperties properties;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails result=null;
        for (Oauth2ClientProperties.Oauth2Client c:properties.getClients()) {
            if(c.getClientId().equals(clientId))return c;
        }
        throw new ClientRegistrationException("invalid clientId :"+clientId);
    }

}
