package com.weilus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
public class Oauth2Controller {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/oauth/confirm_access")
    public String authorize(){
        return "authorize";
    }

    @RequestMapping("/oauth/error")
    public String error(){
        return "error";
    }

    @RequestMapping("/test")
    public @ResponseBody Object xx(){
        System.out.println("test=========================");
        Authentication a =SecurityContextHolder.getContext().getAuthentication();
        if(null != a) {
            System.out.println("SecurityContextHolder==========>" + a.getPrincipal());
        }
        return a.getPrincipal();
    }
}
