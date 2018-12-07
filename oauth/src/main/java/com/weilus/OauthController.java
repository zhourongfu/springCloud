package com.weilus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by liutq on 2018/12/7.
 */
@RestController
public class OauthController {

    @Autowired
    RedisConnectionFactory connectionFactory;
    @Autowired
    private DefaultTokenServices tokenServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientDetailsService auth2ClientService;

    @RequestMapping("/oauth/token")
    public Object oauthToken(String account,String passwd){
        Authentication authentication_req = new UsernamePasswordAuthenticationToken(account,passwd);
        Authentication authentication = authenticationManager.authenticate(authentication_req);
        ClientDetails clientDetails = auth2ClientService.loadClientByClientId("acme");
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientDetails.getClientId(), clientDetails.getScope(), "password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication auth2entication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken accessToken = tokenServices.createAccessToken(auth2entication);
        return accessToken;
    }

}
