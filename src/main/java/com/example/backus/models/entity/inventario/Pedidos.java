package com.example.backus.models.entity.inventario;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PedidosReStock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private LocalDateTime fecha;

    @Column(length = 9000)
    private String productos;

    @Column(length = 5000)
    private String datospago;

    private String estado;

    private String username;
}
