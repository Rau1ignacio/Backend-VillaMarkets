package com.example.backend.controller;

import com.example.backend.dto.ItemPedidoDTO;
import com.example.backend.dto.PedidoDTO;
import com.example.backend.model.ItemPedido;
import com.example.backend.model.Pedido;
import com.example.backend.model.Producto;
import com.example.backend.service.PedidoService;
import com.example.backend.service.ProductoService;
import com.example.backend.service.TiendaService;
import com.example.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final TiendaService tiendaService;

    public PedidoController(PedidoService pedidoService, ProductoService productoService,
                            UsuarioService usuarioService, TiendaService tiendaService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.tiendaService = tiendaService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("Pedido sin items");
        }
        Pedido p = new Pedido();
        p.setTipoEntrega(dto.getTipoEntrega());
        p.setMetodoPago(dto.getMetodoPago());
        p.setDireccionEntrega(dto.getDireccionEntrega());
        if (dto.getUsuarioId() != null) p.setUsuario(usuarioService.findById(dto.getUsuarioId()));
        if (dto.getTiendaId() != null) p.setTienda(tiendaService.findById(dto.getTiendaId()));
        List<ItemPedido> items = dto.getItems().stream().map(it -> {
            Producto prod = productoService.findById(it.getProductoId());
            return ItemPedido.builder()
                    .producto(prod)
                    .cantidad(it.getCantidad())
                    .precioUnitario(it.getPrecioUnitario() == null ? prod.getPrecio() : it.getPrecioUnitario())
                    .build();
        }).collect(Collectors.toList());
        p.setItems(items);
        Pedido saved = pedidoService.create(p);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                pedidoService.listByUsuario(usuarioId).stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<List<PedidoDTO>> listByTienda(@PathVariable Long tiendaId) {
        return ResponseEntity.ok(
                pedidoService.listByTienda(tiendaId).stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<PedidoDTO>> listByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(
                pedidoService.listByAdmin(adminId).stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listAll() {
        return ResponseEntity.ok(
                pedidoService.listAll().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(pedidoService.findById(id)));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> updateEstado(@PathVariable Long id, @RequestParam String estado) {
        Pedido.EstadoPedido est = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
        pedidoService.updateEstado(id, est);
        return ResponseEntity.noContent().build();
    }

    private PedidoDTO toDTO(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .usuarioId(pedido.getUsuario() != null ? pedido.getUsuario().getId() : null)
                .usuarioNombre(pedido.getUsuario() != null ? pedido.getUsuario().getNombres() : null)
                .tiendaId(pedido.getTienda() != null ? pedido.getTienda().getId() : null)
                .tiendaNombre(pedido.getTienda() != null ? pedido.getTienda().getNombre() : null)
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado() != null ? pedido.getEstado().name() : null)
                .tipoEntrega(pedido.getTipoEntrega())
                .metodoPago(pedido.getMetodoPago())
                .total(pedido.getTotal())
                .direccionEntrega(pedido.getDireccionEntrega())
                .items(
                        pedido.getItems() == null
                                ? Collections.emptyList()
                                : pedido.getItems().stream().map(i -> ItemPedidoDTO.builder()
                                        .productoId(i.getProducto() != null ? i.getProducto().getId() : null)
                                        .productoNombre(i.getProducto() != null ? i.getProducto().getNombre() : null)
                                        .imagenProducto(i.getProducto() != null ? i.getProducto().getImagen() : null)
                                        .cantidad(i.getCantidad())
                                        .precioUnitario(i.getPrecioUnitario())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
