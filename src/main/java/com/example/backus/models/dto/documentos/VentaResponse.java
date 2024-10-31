package com.example.backus.models.dto.documentos;

import com.example.backus.models.dto.users.UserResponse;
import com.example.backus.models.entity.globales.TipoCondicion;
import com.example.backus.models.entity.globales.TipoMoneda;
import com.example.backus.models.entity.globales.TipoPago;
import lombok.Builder;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record VentaResponse(
        UUID id,
        CorrelativoResponse correlativo,
        UserResponse cliente,
        UserResponse usuario,
        TipoCondicion tipocondicion,
        TipoPago tipopago,
        TipoMoneda tipomoneda,
        Double tipo_cambio,
        LocalDateTime fecha_emision,
        LocalDateTime fecha_vencimiento,
        String nota,
        Double gravada,
        Double impuesto,
        Double total,
        LocalDateTime fechapago,
        String formapago
) {
}
