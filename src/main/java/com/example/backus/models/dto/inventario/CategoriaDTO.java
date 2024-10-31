package com.example.backus.models.dto.inventario;

import com.example.backus.models.entity.inventario.Categoria;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ProductoResponse> productos;

    public CategoriaDTO(CategoriaResponse categoria, List<ProductoResponse> productos) {
        this.id = categoria.id();
        this.nombre = categoria.nombre();
        this.descripcion = categoria.descripcion();
        this.productos = productos;
    }
}
