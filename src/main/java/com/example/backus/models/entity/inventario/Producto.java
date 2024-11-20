package com.example.backus.models.entity.inventario;

import com.example.backus.models.entity.globales.Archivo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nombre;
    private String pn;

    @Column(length = 2000)
    private String descripcion;

    private Double garantia_cliente;
    private Double garantia_total;
    //quitar garantias


    private Double Stock;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    private Archivo archivo_Principal;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "producto_archivo",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "archivo_id")
    )
    private List<Archivo> archivos;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
