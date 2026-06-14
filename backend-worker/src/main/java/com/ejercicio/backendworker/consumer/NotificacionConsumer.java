package com.ejercicio.backendworker.consumer;

import com.ejercicio.backendworker.model.Compra;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
public class NotificacionConsumer {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    // Inyección por constructor
    public NotificacionConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    
    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void recibirNotificacion(String mensajeJson) {
        try {
            System.out.println("\n📥 [Worker] Llegó un mensaje de texto plano desde RabbitMQ.");
            
            // Convertimos el JSON manual a nuestra clase local Compra
            Compra compra = objectMapper.readValue(mensajeJson, Compra.class);
            
            System.out.println("✅ [Worker] Mapeo manual exitoso.");
            System.out.println("📦 Procesando compra ID: " + compra.getId());
            System.out.println("🛒 Producto: " + compra.getProducto());
            System.out.println("📧 Correo: " + compra.getClienteEmail());
            
            // 1. Simulación del trabajo pesado (Generación de PDF, correos, etc.)            System.out.println("⏳ Enviando notificación...");
            Thread.sleep(3000);
            System.out.println("👍 Proceso completado para ID: " + compra.getId());

            // 2. El trabajo terminó con éxito. Construimos el mensaje para el frontend.
            String canalDestino = "/topic/notificaciones";
            String mensajeFrontend = "¡Tu compra del producto '" + compra.getProducto() + "' ha sido procesada con éxito! Comprobante enviado a " + compra.getClienteEmail();
            
            // 3. 🚀 EMPUJAR LA DATA POR WEBSOCKET
            // Mandamos el texto directo al canal donde Angular está escuchando en tiempo real
            messagingTemplate.convertAndSend(canalDestino, mensajeFrontend);
            
            System.out.println("✅ [Worker] Notificación en tiempo real enviada al canal: " + canalDestino);


        } catch (Exception e) {
            System.err.println("❌ Error al procesar el JSON internamente: " + e.getMessage());
        }
    }
}