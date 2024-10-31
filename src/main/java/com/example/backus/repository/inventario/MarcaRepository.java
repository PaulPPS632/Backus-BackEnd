package com.example.backus.repository.inventario;

import com.example.backus.models.entity.inventario.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    
}
