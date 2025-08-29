package com.example.authservice.service;


import com.example.authservice.dto.Action;
import com.example.authservice.dto.UserDto;
import com.example.authservice.dto.UserEvent;
import com.example.authservice.dto.UserRequest;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.User;
import com.example.authservice.rabbit.UserEventPublisher;
import com.example.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public UserDto create(UserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);


        if (saved.getRole() == Role.USER) {
            publishEvent(Action.CREATE, saved, request.getPassword());
        }
        return toDto(saved);
    }

    public UserDto update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());

        User saved = userRepository.save(user);


        if (saved.getRole() == Role.USER) {
            publishEvent(Action.UPDATE, saved, request.getPassword());
        }
        return toDto(saved);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        userRepository.delete(user);


        if (user.getRole() == Role.USER) {
            publishEvent(Action.DELETE, user, null);
        }
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return toDto(user);
    }

    public UserDto updateByUsername(String username, UserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!user.getUsername().equals(request.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Имя пользователя уже занято");
            }
            user.setUsername(request.getUsername());
        }
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);

        if (saved.getRole() == Role.USER) {
            publishEvent(Action.UPDATE, saved, request.getPassword());
        }

        return toDto(saved);
    }

    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        userRepository.delete(user);

        if (user.getRole() == Role.USER) {
            publishEvent(Action.DELETE, user, null);
        }
    }

    private void publishEvent(Action action, User user, String rawPassword) {
        UserEvent event = UserEvent.builder()
                .action(action)
                .username(user.getUsername())
                .email(user.getEmail())
                .password(rawPassword)
                .recipients(getAdminEmails())
                .occurredAt(Instant.now())
                .build();

        userEventPublisher.publish(event);
    }

    private List<String> getAdminEmails() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.ADMIN)
                .map(User::getEmail)
                .toList();
    }
}
