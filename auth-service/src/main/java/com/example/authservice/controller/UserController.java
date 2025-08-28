package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.dto.UserRequest;
import com.example.authservice.security.CustomUserDetailsService;
import com.example.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j // <- для логирования
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping
    public ResponseEntity<UserDto> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        log.info("Пользователь {} запрашивает свои данные", username);

        UserDto user = userService.findByUsername(username);

        log.info("Данные пользователя {} успешно получены", username);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateMyProfile(Authentication authentication,
                                                   @RequestBody UserRequest request) {
        String username = authentication.getName();
        log.info("Пользователь {} обновляет свои данные", username);

        UserDto updatedUser = userService.updateByUsername(username, request);

        log.info("Пользователь {} успешно обновил свои данные", username);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMyProfile(Authentication authentication) {
        String username = authentication.getName();
        log.info("Пользователь {} пытается удалить свой аккаунт", username);

        userService.deleteByUsername(username);

        log.info("Пользователь {} успешно удалил свой аккаунт", username);
        return ResponseEntity.noContent().build();
    }
}


