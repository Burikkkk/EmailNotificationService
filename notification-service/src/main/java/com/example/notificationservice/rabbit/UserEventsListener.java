package com.example.notificationservice.rabbit;

import com.example.notificationservice.config.RabbitConfig;
import com.example.notificationservice.dto.Action;
import com.example.notificationservice.dto.UserEvent;
import com.example.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventsListener {

    private final EmailService mail;

    @RabbitListener(
            queues = RabbitConfig.QUEUE,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void onUserChanged(UserEvent event) {
        log.info("Получено событие из RabbitMQ: action={}, username={}, email={}, recipients={}",
                event.getAction(), event.getUsername(), event.getEmail(), event.getRecipients());

        String subject = switch (event.getAction()) {
            case CREATE -> "Создан пользователь %s".formatted(event.getUsername());
            case UPDATE -> "Изменен пользователь %s".formatted(event.getUsername());
            case DELETE -> "Удален пользователь %s".formatted(event.getUsername());
            default -> "Событие по пользователю %s".formatted(event.getUsername());
        };

        String text;
        if (event.getAction() == Action.DELETE) {
            text = "%s пользователь с именем %s и почтой %s.".formatted(
                    actionType(event.getAction()),
                    event.getUsername(),
                    event.getEmail()
            );
        } else {
            text = "%s пользователь с именем %s, паролем %s и почтой %s.".formatted(
                    actionType(event.getAction()),
                    event.getUsername(),
                    event.getPassword(),
                    event.getEmail()
            );
        }

        if (event.getRecipients() != null && !event.getRecipients().isEmpty()) {
            for (String to : event.getRecipients()) {
                try {
                    mail.sendEmail(to, subject, text);
                } catch (Exception ex) {
                    log.error("Ошибка при отправке письма на {}", to, ex);
                }
            }
        } else {
            log.warn("Нет получателей для события пользователя {}", event.getUsername());
        }
    }

    private String actionType(Action action) {
        return switch (action) {
            case CREATE -> "Создан";
            case UPDATE -> "Изменен";
            case DELETE -> "Удален";
        };
    }
}
