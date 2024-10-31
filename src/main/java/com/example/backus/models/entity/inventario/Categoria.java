package com.example.backus.models.entity.inventario;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String Descripcion;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Producto> producto;
}

