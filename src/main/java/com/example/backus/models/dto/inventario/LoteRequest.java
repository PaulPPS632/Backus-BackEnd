package com.example.backus.models.dto.inventario;

import java.time.LocalDateTime;

public record LoteRequest(String id_producto, String nombre, LocalDateTime fecha) {
    
}