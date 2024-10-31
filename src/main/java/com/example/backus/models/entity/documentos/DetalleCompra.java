package com.example.backus.models.entity.documentos;

import com.example.backus.models.entity.inventario.Producto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DetalleCompra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_compra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;


    private int cantidad;

    private Double precio_unitario;

    private Double precio_neto;

    private Double precio_bruto;

    private Double impuesto;

    private Double total;

}
