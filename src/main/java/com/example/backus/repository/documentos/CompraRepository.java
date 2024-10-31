package com.example.backus.repository.documentos;

import com.example.backus.models.entity.documentos.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompraRepository extends JpaRepository<Compra, UUID> {
}
