package com.weilus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by liutq on 2018/5/21.
 */
@EnableAutoConfiguration
@Configuration
@EnableOAuth2Sso
@RestController
public class ClientApplication {


    @RequestMapping("/")
    public String home(Principal user) {
        return "Hello " + user.getName();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
