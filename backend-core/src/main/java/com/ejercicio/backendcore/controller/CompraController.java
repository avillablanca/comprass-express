package com.ejercicio.backendcore.controller;

import com.ejercicio.backendcore.model.Compra;
import com.ejercicio.backendcore.model.CompraRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*") // Permite que Angular se conecte sin problemas de CORS
public class CompraController {

    private final CompraRepository compraRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    // Inyección de dependencias por constructor (Buena práctica)
    public CompraController(CompraRepository compraRepository, RabbitTemplate rabbitTemplate) {
        this.compraRepository = compraRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<String> procesarCompra(@RequestBody Compra compra) {
        try {
            // 1. Guardar de forma sincrónica en la base de datos H2
            Compra compraGuardada = compraRepository.save(compra);
            System.out.println("💾 Compra guardada en BD con ID: " + compraGuardada.getId());

            // 2. Publicar de forma asíncrona en la cola de RabbitMQ
            // Gracias a la Opción 2 del paso anterior, rabbitTemplate convierte la entidad a JSON automáticamente
            rabbitTemplate.convertAndSend(queueName, compraGuardada);
            System.out.println("🚀 Mensaje enviado a la cola '" + queueName + "' para el ID: " + compraGuardada.getId());

            // 3. Responder de inmediato al cliente (Angular)
            return ResponseEntity.ok("Compra recibida exitosamente. Procesando notificaciones en segundo plano.");
            
        } catch (Exception e) {
            System.err.println("❌ Error al procesar la compra: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno al procesar la transacción.");
        }
    }
}