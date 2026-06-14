package com.ejercicio.backendworker.config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQWorkerConfig {

    @Bean
    public MessageConverter simpleMessageConverter() {
        // Al usar el SimpleMessageConverter, Spring AMQP no buscará Jackson ni intentará resolver clases.
        // Tratará el cuerpo del mensaje como un arreglo de bytes o un String plano de forma directa.
        return new SimpleMessageConverter();
    }
}