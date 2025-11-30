package com.example.backend.service;

import com.example.backend.model.ItemPedido;
import com.example.backend.model.Pedido;
import com.example.backend.model.Producto;
import com.example.backend.repository.PedidoRepository;
import com.example.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;

    public PedidoService(PedidoRepository pedidoRepo, ProductoRepository productoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
    }

    public Pedido create(Pedido pedido) {
        // validar stock y calcular totales
        BigDecimal total = BigDecimal.ZERO;
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new RuntimeException("Pedido sin items");
        }
        for (ItemPedido it : pedido.getItems()) {
            Producto p = productoRepo.findById(it.getProducto().getId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            int stock = p.getStock() == null ? 0 : p.getStock();
            if (stock < it.getCantidad()) throw new RuntimeException("Stock insuficiente para " + p.getNombre());
            // decrementar stock
            p.setStock(stock - it.getCantidad());
            productoRepo.save(p);

            BigDecimal subtotal = it.getPrecioUnitario().multiply(BigDecimal.valueOf(it.getCantidad()));
            total = total.add(subtotal);
            it.setPedido(pedido); // vincular
        }
        pedido.setTotal(total);
        pedido.setFechaPedido(LocalDateTime.now());
        return pedidoRepo.save(pedido);
    }

    public Pedido updateEstado(Long pedidoId, Pedido.EstadoPedido estado) {
        Pedido p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        p.setEstado(estado);
        return pedidoRepo.save(p);
    }

    public Pedido findById(Long id) {
        return pedidoRepo.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public List<Pedido> listByUsuario(Long usuarioId) {
        return pedidoRepo.findByUsuarioId(usuarioId);
    }

    public List<Pedido> listAll() {
        return pedidoRepo.findAll();
    }
}