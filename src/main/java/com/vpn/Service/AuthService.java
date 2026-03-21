package com.vpn.Service;

import com.vpn.DTO.CreateUserRequest;
import com.vpn.DTO.LoginRequest;
import com.vpn.DTO.LoginResponse;
import com.vpn.Entity.User;
import com.vpn.Repository.UserRepository;
import com.vpn.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getHashpassword())){
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.GenerateToken(
                user.getId(),
                user.getHashpassword(),
                user.getRole()
        );

        return new LoginResponse (token);
    }
}
