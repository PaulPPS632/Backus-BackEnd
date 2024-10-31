package com.example.backus.models.dto.inventario;

import com.example.backus.models.entity.globales.Archivo;
import com.example.backus.models.entity.inventario.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaProductoDTO {
    private String categoria;
    private ProductoResponse producto;
    public CategoriaProductoDTO(String categoria, Producto producto) {
        this.categoria = categoria;
        this.producto = mapToProductoResponse(producto);
    }
    private ProductoResponse mapToProductoResponse(Producto producto){
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getPn(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getMarca().getNombre(),
                producto.getCategoria().getNombre(),
                producto.getGarantia_cliente(),
                producto.getGarantia_total(),
                producto.getArchivo_Principal() != null ? producto.getArchivo_Principal().getUrl() : "",
                producto.getArchivos().stream().map(Archivo::getUrl).collect(Collectors.toList())
        );
    }
}
