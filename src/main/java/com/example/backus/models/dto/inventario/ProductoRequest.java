package com.example.backus.models.dto.inventario;

import java.util.List;

public record ProductoRequest(
    String id,
    String nombre, 
    String pn,
    String descripcion,
    Double stock,
    Double precio,
    Long id_marca,
    Long id_categoria,
    Double garantia_cliente,
    Double garantia_total,
    String imagen_principal,
    List<String> imageurl
    ) {
}
