package com.example.backus.repository.correlativos;

import com.example.backus.models.entity.correlativos.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoComprobanteRepository extends JpaRepository<TipoComprobante, Long> {
    Optional<TipoComprobante> findByPrefijo(String prefijo);
}
