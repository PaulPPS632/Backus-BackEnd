package com.example.backus.models.dto.inventario;

import com.example.backus.models.dto.users.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PedidosReStockResponse(
        String id,
        UserResponse usuario,
        LocalDateTime fecha,
        ProductoResponse producto,
        String estado,
        Long cantidad,
        String nota,
        String tenantId
) {
}


