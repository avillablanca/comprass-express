package com.ejercicio.backendcore.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    // Listo, JpaRepository ya tiene los métodos save(), findAll(), etc. creados internamente.
}