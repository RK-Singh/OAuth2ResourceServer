package com.rk.oauth.oauthlearn;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourceController {
    @RequestMapping({"/user","/me"})
    public Principal user(Principal principal){
        return principal;
    }

    @RequestMapping({"/test"})
    public String test(){
        return "This is a protected endpoint";
    }

}
