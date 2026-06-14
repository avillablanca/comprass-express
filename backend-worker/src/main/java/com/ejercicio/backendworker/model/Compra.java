package com.ejercicio.backendworker.model;

import java.io.Serializable;

public class Compra implements Serializable {
    private Long id;
    private String producto;
    private Double precio;
    private String clienteEmail;
    private String fechaCompra;

    // Constructor vacío obligatorio para Jackson
    public Compra() {}

    // Getters y Setters
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