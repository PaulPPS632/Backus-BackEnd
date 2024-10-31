package com.example.backus.repository.inventario;

import com.example.backus.models.entity.inventario.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidosReStockRepository extends JpaRepository<Pedidos, String> {
    List<Pedidos> findTop100ByOrderByFechaDesc();
    List<Pedidos> findByUsername(String username);
}
