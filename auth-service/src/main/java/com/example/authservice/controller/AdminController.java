package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.dto.UserRequest;
import com.example.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("Администратор запрашивает список всех пользователей");
        List<UserDto> users = userService.findAll();
        log.info("Список пользователей успешно получен, количество: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        log.info("Администратор запрашивает пользователя с id={}", id);
        UserDto user = userService.findById(id);
        log.info("Данные пользователя с id={} успешно получены", id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserRequest request) {
        log.info("Администратор создаёт нового пользователя: {}", request.getUsername());
        UserDto createdUser = userService.create(request);
        log.info("Пользователь {} успешно создан с id={}", createdUser.getUsername(), createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,
                                          @RequestBody UserRequest request) {
        log.info("Администратор обновляет пользователя с id={}", id);
        UserDto updatedUser = userService.update(id, request);
        log.info("Пользователь с id={} успешно обновлён", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Администратор удаляет пользователя с id={}", id);
        userService.delete(id);
        log.info("Пользователь с id={} успешно удалён", id);
        return ResponseEntity.noContent().build();
    }
}

