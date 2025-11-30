package com.example.backend.repository;

import com.example.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByFechaPedidoDesc(Long usuarioId);
    List<Pedido> findAllByOrderByFechaPedidoDesc();
    List<Pedido> findByTiendaIdOrderByFechaPedidoDesc(Long tiendaId);
    List<Pedido> findByTiendaVendedorIdOrderByFechaPedidoDesc(Long vendedorId);
}
