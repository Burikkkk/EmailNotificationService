package com.example.notificationservice.dto;


import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEvent {
    private Action action;
    private String username;
    private String email;
    private String password;
    private List<String> recipients;
    private Instant occurredAt;
}
