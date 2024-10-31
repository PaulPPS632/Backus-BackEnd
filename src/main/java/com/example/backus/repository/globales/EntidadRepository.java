package com.example.backus.repository.globales;

import com.example.backus.models.entity.globales.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntidadRepository extends JpaRepository<Entidad, String> {
    List<Entidad> findByDocumentoContaining(String keyword);

    Optional<Entidad> findByDocumento(String Documento);
}
