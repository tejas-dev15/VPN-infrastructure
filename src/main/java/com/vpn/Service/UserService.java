package com.vpn.Service;

import com.vpn.DTO.CreateUserRequest;
import com.vpn.Entity.User;
import com.vpn.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User CreateUser(CreateUserRequest request){

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .hashpassword(hashedPassword)
                .role(request.getRole() == null ? "USER" : request.getRole())
                .build();

        return userRepository.save(user);
    }
}
