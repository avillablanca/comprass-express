package com.ejercicio.backendcore.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Leemos el nombre de la cola desde el archivo application.properties
    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue queue() {
        // Creamos una cola durable (no se borra si el broker se reinicia)
        return new Queue(queueName, true);
    }

    // Convertidor crucial: Por defecto Spring envía los mensajes como objetos Java serializados (binarios).
    // Con este Bean, obligamos a Spring a transformar nuestros objetos automáticamente a JSON puro.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}