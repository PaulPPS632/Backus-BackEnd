package com.example.backus.service.globales;

import com.example.backus.models.dto.documentos.CorrelativoResponse;
import com.example.backus.models.dto.documentos.DetalleVentaResponse;
import com.example.backus.models.dto.documentos.VentaResponse;
import com.example.backus.models.dto.users.RolResponse;
import com.example.backus.models.dto.users.UserResponse;
import com.example.backus.models.entity.correlativos.Correlativo;
import com.example.backus.models.entity.documentos.DetalleVenta;
import com.example.backus.models.entity.documentos.Venta;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repository.documentos.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    public List<VentaResponse> findAll() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream().map(this::mapToVentaResponse).toList();
    }
    private VentaResponse mapToVentaResponse(Venta venta){
        return new VentaResponse(
                venta.getId(),
                mapToCorrelativoResponse(venta.getCorrelativo()),
                maptoUserResponse(venta.getUsuario_negocio()),
                maptoUserResponse(venta.getUsuario()),
                venta.getTipocondicion(),
                venta.getTipopago(),
                venta.getTipomoneda(),
                venta.getTipo_cambio(),
                venta.getFecha_emision(),
                venta.getFecha_vencimiento(),
                venta.getNota(),
                venta.getGravada(),
                venta.getImpuesto(),
                venta.getTotal(),
                venta.getFechapago(),
                venta.getFormapago()
        );
    }
    private CorrelativoResponse mapToCorrelativoResponse(Correlativo correlativo){
        String prefijo = correlativo.getNumeracioncomprobante().getTipocomprobante().getPrefijo();
        Long numeracion = correlativo.getNumeracioncomprobante().getNumeracion();
        Long corre = correlativo.getNumero();
        String documento = prefijo + String.format("%04d", numeracion) + "-" + corre;
        return new CorrelativoResponse(prefijo,numeracion,corre,documento);
    }
    /*
    private DetalleVentaResponse mapToDetalleVentaResponse(DetalleVenta detalleVenta){
        return new DetalleVentaResponse(
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidad(),
                detalleVenta.getPrecio_unitario(),
                detalleVenta.getPrecio_neto(),
                detalleVenta.getPrecio_bruto(),
                detalleVenta.getImpuesto(),
                detalleVenta.getTotal()
        );
    }*/
    private UserResponse maptoUserResponse(Users user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .adress(user.getAdress())
                .phone(user.getPhone())
                .document(user.getDocument())
                .rol(maptoRolResponse(user.getRoles()))
                .build();
    }
    private RolResponse maptoRolResponse(Roles rol){
        if(rol != null){
            return RolResponse.builder()
                    .id(rol.getId())
                    .nombre(rol.getName())
                    .descripcion(rol.getDescription())
                    .build();
        }
        return RolResponse.builder().build();
    }
}
