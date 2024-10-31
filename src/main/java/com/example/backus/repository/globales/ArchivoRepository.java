package com.example.backus.repository.globales;

import com.example.backus.models.entity.globales.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchivoRepository extends JpaRepository<Archivo,String> {
    @Query("SELECT a FROM Archivo a WHERE a.tipo_Archivo = :tipo ORDER BY a.descripcion")
    List<Archivo> findAllByTipoOrderByDescripcion(String tipo);
}
