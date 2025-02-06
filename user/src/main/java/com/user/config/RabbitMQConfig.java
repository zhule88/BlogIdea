package com.user.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue emailQueue() {
        return new Queue("email");
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("email");
    }

    @Bean
    public Binding userBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("email");
    }
}