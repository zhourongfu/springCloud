package com.weilus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
