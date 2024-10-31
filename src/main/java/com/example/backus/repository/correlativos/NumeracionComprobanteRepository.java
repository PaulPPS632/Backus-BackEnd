package com.example.backus.repository.correlativos;

import com.example.backus.models.entity.correlativos.NumeracionComprobante;
import com.example.backus.models.entity.correlativos.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumeracionComprobanteRepository extends JpaRepository<NumeracionComprobante, Long> {

    Optional<NumeracionComprobante> findByTipocomprobanteAndNumeracion(TipoComprobante tipocomprobante, Long numeracion);

    //@Query("SELECT nc FROM NumeracionComprobante nc WHERE nc.tipocomprobante.id = :id_tipocomprobante AND nc.numeracion = :numeracion")
    //Optional<NumeracionComprobante> buscar(@Param("numeracion") Long numeracion, @Param("id_tipocomprobante") Long id_tipocomprobante);

    //@Query("SELECT nc FROM NumeracionComprobante nc WHERE nc.tipocomprobante = :id_tipocomprobante AND nc.numeracion = :numeracion")
    //Optional<NumeracionComprobante> findByTipocomprobanteAndNumeracion(@Param("numeracion") Long numeracion, @Param("id_tipocomprobante") Long id_tipocomprobante);

}
