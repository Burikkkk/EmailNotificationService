package com.example.authservice.dto;

import com.example.authservice.entity.Role;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role; // Можно ограничить только для ADMIN
}
