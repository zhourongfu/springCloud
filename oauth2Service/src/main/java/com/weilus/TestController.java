package com.weilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/oauth/confirm_access")
    public String authorize(HttpServletRequest request, Model model){
        model.addAttribute("access_token",request.getParameter("access_token"));
        model.addAttribute("redirect_uri",request.getParameter("redirect_uri"));
        return "authorize";
    }

    @RequestMapping("/oauth/error")
    public String error(){
        return "error";
    }

    @RequestMapping("/test")
    public @ResponseBody Object xx(Principal principal){
        logger.info("principal===========>"+principal);
        return principal;
    }
}
