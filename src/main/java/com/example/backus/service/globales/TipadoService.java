package com.example.backus.service.globales;

import com.example.backus.models.dto.documentos.TipoComprobanteResponse;
import com.example.backus.models.dto.globales.TipadoDocumentos;
import com.example.backus.models.entity.correlativos.TipoComprobante;
import com.example.backus.models.entity.globales.TipoCondicion;
import com.example.backus.models.entity.globales.TipoMoneda;
import com.example.backus.models.entity.globales.TipoPago;
import com.example.backus.repository.correlativos.TipoComprobanteRepository;
import com.example.backus.repository.correlativos.TipoCondicionRepository;
import com.example.backus.repository.correlativos.TipoMonedaRepository;
import com.example.backus.repository.correlativos.TipoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipadoService {

    private final TipoComprobanteRepository tipoComprobanteRepository;
    private final TipoCondicionRepository tipoCondicionRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final TipoMonedaRepository tipoMonedaRepository;

    public TipadoDocumentos get() {
        List<TipoComprobante> tipoComprobante = tipoComprobanteRepository.findAll();
        List<TipoCondicion> tipoCondicion = tipoCondicionRepository.findAll();
        List<TipoPago> tipoPago = tipoPagoRepository.findAll();
        List<TipoMoneda> tipoMoneda = tipoMonedaRepository.findAll();

        TipadoDocumentos nuevo = new TipadoDocumentos(tipoComprobante.stream().map(this::mapToTipoComprobanteResponse).toList(),tipoCondicion,tipoPago,tipoMoneda);
        return nuevo;
    }
    private TipoComprobanteResponse mapToTipoComprobanteResponse(TipoComprobante tipoComprobante) {
        return new TipoComprobanteResponse(tipoComprobante.getId(), tipoComprobante.getPrefijo(), tipoComprobante.getDescripcion());
    }
}
