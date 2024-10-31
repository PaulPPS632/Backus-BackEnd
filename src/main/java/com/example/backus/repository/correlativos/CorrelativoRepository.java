package com.example.backus.repository.correlativos;

import com.example.backus.models.entity.correlativos.Correlativo;
import com.example.backus.models.entity.correlativos.NumeracionComprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorrelativoRepository extends JpaRepository<Correlativo, UUID> {
    long countByNumeracioncomprobante(NumeracionComprobante numeracioncomprobante);
}
