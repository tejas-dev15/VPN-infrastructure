package com.vpn.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Healthcheck {

    @GetMapping("/healthCheck")
    public String healthcheck(){
        return "Health OK";
    }
}
