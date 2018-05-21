package com.weilus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by liutq on 2018/5/21.
 */
@SpringBootApplication
@RestController
public class UAAApplication {


    public static void main(String[] args) {
        SpringApplication.run(UAAApplication.class,args);
    }

    @RequestMapping("/me")
    public Principal user(Principal user) {
        return user;
    }


}
