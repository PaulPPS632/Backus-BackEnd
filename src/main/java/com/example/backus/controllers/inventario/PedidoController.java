package com.example.backus.controllers.inventario;

import com.example.backus.models.entity.inventario.Pedidos;
import com.example.backus.service.inventario.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidosService pedidosReStockService;

    @PostMapping
    public void registrar(@RequestBody Pedidos pedido){
        pedidosReStockService.registrar(pedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedidos>> listar(){

        return pedidosReStockService.Lista();
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<Pedidos>> getPedidosByUsername(@PathVariable("username") String username){
        return pedidosReStockService.getPedidosByUsername(username);
    }
    @PutMapping()
    public void cambios(@RequestBody Pedidos pedidos){
        pedidosReStockService.cambios(pedidos);
    }

}
