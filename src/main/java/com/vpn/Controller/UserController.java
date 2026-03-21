package com.vpn.Controller;

import com.vpn.DTO.CreateUserRequest;
import com.vpn.Entity.User;
import com.vpn.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public User CreateUsers(@Valid @RequestBody CreateUserRequest request){
        return userService.CreateUser(request);
    }
}
