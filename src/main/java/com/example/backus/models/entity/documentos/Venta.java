package com.example.backus.models.entity.documentos;

import com.example.backus.models.entity.correlativos.Correlativo;
import com.example.backus.models.entity.globales.Entidad;
import com.example.backus.models.entity.globales.TipoCondicion;
import com.example.backus.models.entity.globales.TipoMoneda;
import com.example.backus.models.entity.globales.TipoPago;
import com.example.backus.models.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Venta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_correlativo")
    private Correlativo correlativo;


    @ManyToOne
    @JoinColumn(name = "usuario_negocio_id")
    private Users usuario_negocio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Users usuario;

    @ManyToOne
    @JoinColumn(name = "id_tipocondicion")
    private TipoCondicion tipocondicion;

    @ManyToOne
    @JoinColumn(name = "id_tipopago")
    private TipoPago tipopago;

    private LocalDateTime fecha_emision;
    private LocalDateTime fecha_vencimiento;

    @Column(length = 2000)
    private String nota;

    private Double gravada;
    private Double impuesto;
    private Double total;
    private LocalDateTime fechapago;
    private String formapago;
    private String url_pdf;

    @ManyToOne
    @JoinColumn(name = "id_tipomoneda")
    private TipoMoneda tipomoneda;

    private Double tipo_cambio;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVenta;


}
