package com.vpn.Controller;

import com.vpn.Service.SSHService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Healthcheck {

    private final SSHService sshService;

    @GetMapping("/healthCheck")
    public String healthcheck() {
        return "Health OK";
    }


    @GetMapping("/test-ssh")
    public String testSSH() {
        return sshService.executeCommand("whoami");
    }
}