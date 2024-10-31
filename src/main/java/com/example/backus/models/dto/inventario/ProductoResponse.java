package com.example.backus.models.dto.inventario;

import java.util.List;

public record ProductoResponse(
    String id,
    String nombre, 
    String pn,
    String descripcion,
    Double stock,
    Double precio,
    String marca,
    String categoria,
    Double garantia_cliente,
    Double garantia_total,
    String imagen_principal,
    List<String> imageurl
    ) {
    
}
