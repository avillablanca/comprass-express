package com.ejercicio.backendcore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity // Le dice a JPA que esto es una tabla de base de datos
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String producto;
    private Double precio;
    private String clienteEmail;
    private String fechaCompra;

    // Constructor vacío requerido por JPA
    public Compra() {
        this.fechaCompra = LocalDateTime.now().toString();
    }

    public Compra(String producto, Double precio, String clienteEmail) {
        this.producto = producto;
        this.precio = precio;
        this.clienteEmail = clienteEmail;
        this.fechaCompra = LocalDateTime.now().toString();
        
    }

    // Getters y Setters (los usará Spring para serializar a JSON)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(String fechaCompra) { this.fechaCompra = fechaCompra; }

    
}