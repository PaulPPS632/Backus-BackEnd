package com.example.backus.models.dto.documentos;

import lombok.Builder;

@Builder
public record DetalleVentaResponse(
        String Producto,
        int cantidad,
        Double precio_unitario,
        Double precio_neto,
        Double precio_bruto,
        Double impuesto,
        Double total
) {
}
