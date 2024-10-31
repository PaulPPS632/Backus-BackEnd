package com.example.backus.models.entity.documentos;

import com.example.backus.models.entity.inventario.Producto;
import jakarta.persistence.*;
import lombok.*;

import java.nio.DoubleBuffer;

@Entity
@Table(name = "DetalleVenta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private String Producto;

    private int cantidad;

    private Double precio_unitario;

    private Double precio_neto;

    private Double precio_bruto;

    private Double impuesto;

    private Double total;

}
