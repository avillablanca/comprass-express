package com.ejercicio.backendworker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Habilita el manejo de mensajes de WebSocket respaldado por un broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker de mensajes simple en memoria para llevar las notificaciones al frontend.
        // Los canales que comiencen con "/topic" serán a los que se suscribirá Angular.
        config.enableSimpleBroker("/topic");
        
        // Prefijo para los mensajes que van desde el frontend hacia el backend (no los usaremos mucho ahora, pero es estándar)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // El punto de entrada HTTP donde el frontend iniciará la conexión WebSocket (Handshake)
        registry.addEndpoint("/ws-notificaciones")
                .setAllowedOrigins("*"); // Permite conexiones desde tu app de Angular sin problemas de CORS
    }
}