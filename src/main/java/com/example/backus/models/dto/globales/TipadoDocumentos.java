package com.example.backus.models.dto.globales;

import com.example.backus.models.dto.documentos.TipoComprobanteResponse;
import com.example.backus.models.entity.globales.TipoCondicion;
import com.example.backus.models.entity.globales.TipoMoneda;
import com.example.backus.models.entity.globales.TipoPago;

import java.util.List;

public record TipadoDocumentos(
        List<TipoComprobanteResponse> tipocomprobantes,
        List<TipoCondicion> tipocondiciones,
        List<TipoPago> tipopagos,
        List<TipoMoneda> tipomonedas
) {
}
