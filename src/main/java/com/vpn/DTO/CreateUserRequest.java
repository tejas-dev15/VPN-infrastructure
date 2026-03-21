package com.vpn.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    @Size(min = 4 , max =20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

    private String role;
}
