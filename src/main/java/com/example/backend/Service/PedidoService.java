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
        if (pedido == null || pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new RuntimeException("Pedido sin items");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItems()) {
            if (item.getProducto() == null || item.getProducto().getId() == null) {
                throw new RuntimeException("Producto no especificado en item");
            }

            Producto producto = productoRepo.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            int stockDisponible = producto.getStock() == null ? 0 : producto.getStock();
            int cantidadSolicitada = item.getCantidad() == null ? 0 : item.getCantidad();
            if (cantidadSolicitada <= 0) {
                throw new RuntimeException("Cantidad invalida para " + producto.getNombre());
            }
            if (stockDisponible < cantidadSolicitada) {
                throw new RuntimeException("Stock insuficiente para " + producto.getNombre());
            }

            producto.setStock(stockDisponible - cantidadSolicitada);
            productoRepo.save(producto);

            item.setProducto(producto);
            if (item.getPrecioUnitario() == null) {
                item.setPrecioUnitario(producto.getPrecio());
            }
            item.setPedido(pedido);

            BigDecimal precio = item.getPrecioUnitario() == null ? BigDecimal.ZERO : item.getPrecioUnitario();
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidadSolicitada));
            total = total.add(subtotal);
        }

        pedido.setTotal(total);
        if (pedido.getFechaPedido() == null) {
            pedido.setFechaPedido(LocalDateTime.now());
        }
        return pedidoRepo.save(pedido);
    }

    public Pedido updateEstado(Long pedidoId, Pedido.EstadoPedido estado) {
        Pedido p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        p.setEstado(estado);
        return pedidoRepo.save(p);
    }

    @Transactional(readOnly = true)
    public Pedido findById(Long id) {
        Pedido pedido = pedidoRepo.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        preloadItems(pedido);
        return pedido;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listByUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new RuntimeException("usuarioId requerido");
        }
        List<Pedido> pedidos = pedidoRepo.findByUsuarioIdOrderByFechaPedidoDesc(usuarioId);
        pedidos.forEach(this::preloadItems);
        return pedidos;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listAll() {
        List<Pedido> pedidos = pedidoRepo.findAllByOrderByFechaPedidoDesc();
        pedidos.forEach(this::preloadItems);
        return pedidos;
    }

    private void preloadItems(Pedido pedido) {
        if (pedido != null && pedido.getItems() != null) {
            pedido.getItems().forEach(item -> {
                if (item.getProducto() != null) {
                    item.getProducto().getId();
                }
            });
        }
    }
}
