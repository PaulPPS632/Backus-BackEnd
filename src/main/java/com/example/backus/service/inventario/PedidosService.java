package com.example.backus.service.inventario;

import com.example.backus.service.User.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backus.models.dto.inventario.PedidosReStockRequest;
import com.example.backus.models.dto.inventario.PedidosReStockResponse;
import com.example.backus.models.dto.inventario.ProductoResponse;
import com.example.backus.models.dto.users.RolResponse;
import com.example.backus.models.dto.users.UserResponse;
import com.example.backus.models.entity.globales.Archivo;
import com.example.backus.models.entity.inventario.Pedidos;
import com.example.backus.models.entity.inventario.Producto;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repository.inventario.PedidosReStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidosService {

    private final PedidosReStockRepository pedidosReStockRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    public void registrar(Pedidos pedido){
        try {
            JsonNode rootNode = objectMapper.readTree(pedido.getDatospago());
            JsonNode billingDetails = rootNode.path("customer")
                    .path("billingDetails");
            String IdentityCode = billingDetails.path("identityCode").asText();
            pedido.setFecha(LocalDateTime.now());
            pedidosReStockRepository.save(pedido);
            userService.UserRegisterJson(IdentityCode,
                    billingDetails.path("firstName").asText() +" " +billingDetails.path("lastName").asText(),
                    billingDetails.path("address").asText(),
                    billingDetails.path("cellPhoneNumber").asText(),
                    rootNode.path("customer").path("email").asText(),
                    "DNI"
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }
    public ResponseEntity<List<Pedidos>> Lista(){
        List<Pedidos> pedidos = pedidosReStockRepository.findTop100ByOrderByFechaDesc();
        if(pedidos.isEmpty()){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
        return ResponseEntity.ok(pedidos);
    }
    public void editar(PedidosReStockRequest pedido){
        Pedidos actual = pedidosReStockRepository.findById(pedido.id()).orElseThrow();
        pedidosReStockRepository.save(Pedidos.builder()
                .id(actual.getId())
                .fecha(pedido.fecha())
                .estado(pedido.estado())
                .build());
    }
    private PedidosReStockResponse mapToPedidosReStockResponse(Pedidos pedido){
        return PedidosReStockResponse.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado())
                .build();
    }
    public ProductoResponse mapToProductoResponse(Producto producto){
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

    public ResponseEntity<List<Pedidos>> getPedidosByUsername(String username) {
        return ResponseEntity.ok(pedidosReStockRepository.findByUsername(username));
    }

    public void cambios(Pedidos pedido) {
        pedidosReStockRepository.save(pedido);
    }
}
