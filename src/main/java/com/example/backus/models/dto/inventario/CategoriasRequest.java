package com.example.backus.models.dto.inventario;

import java.util.List;

public record CategoriasRequest(
        List<CategoriaRequest> categorias
) {
}
