package com.example.authservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "user-events";
    public static final String EXCHANGE = "user-exchange";
    public static final String ROUTING_KEY = "user.event";

    @Bean
    Queue userQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    DirectExchange userExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    Binding userBinding(Queue q, DirectExchange ex) {
        return BindingBuilder.bind(q).to(ex).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        classMapper.setIdClassMapping(Collections.emptyMap());
        converter.setClassMapper(classMapper);
        return converter;
    }
}

