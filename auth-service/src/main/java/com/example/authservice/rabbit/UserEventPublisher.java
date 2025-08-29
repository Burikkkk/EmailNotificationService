package com.example.authservice.rabbit;

import com.example.authservice.config.RabbitConfig;
import com.example.authservice.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(UserEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event,
                message -> {
                    message.getMessageProperties().getHeaders().remove("__TypeId__");
                    return message;
                }
        );
        log.info("Опубликовано событие: {} для пользователя {}", event.getAction(), event.getUsername());
    }
}