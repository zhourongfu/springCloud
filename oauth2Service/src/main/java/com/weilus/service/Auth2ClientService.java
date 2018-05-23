package com.weilus.service;

import com.weilus.dao.Auth2ClientDao;
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
    private Auth2ClientDao auth2ClientDao;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return auth2ClientDao.loadClientByClientId(clientId);
    }


}
