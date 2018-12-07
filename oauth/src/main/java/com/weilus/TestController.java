package com.weilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/login")
    public String login(){
        return "authorize_login";
    }

    @RequestMapping("/oauth/confirm_access")
    public String authorize(){
        return "authorize";
    }

    @RequestMapping("/oauth/error")
    public String error(){
        return "error";
    }
    @RequestMapping("/sso/login")
    public String index(){
        return "sso_login";
    }
    @RequestMapping("/")
    public @ResponseBody  Principal index(Principal user){
        return user;
    }

    @RequestMapping("/test")
    public @ResponseBody Object xx(){
        String msg = "test call successful !!!!!!!";
        logger.info(msg);
        return msg;
    }
}
