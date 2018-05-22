package com.weilus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by liutq on 2018/5/21.
 */
@SpringBootApplication
@RestController
public class ClientApplication {


    @RequestMapping("/client")
    public Principal home(Principal user)
    {
        return user;
    }


    @RequestMapping("/hello")
    public String hello() {
        return "Hello world!";
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class).run(args);
    }

}
