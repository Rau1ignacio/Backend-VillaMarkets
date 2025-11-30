package com.example.backend.controller;

import com.example.backend.dto.PedidoDTO;
import com.example.backend.dto.ItemPedidoDTO;
import com.example.backend.model.Pedido;
import com.example.backend.model.ItemPedido;
import com.example.backend.model.Producto;
import com.example.backend.service.PedidoService;
import com.example.backend.service.ProductoService;
import com.example.backend.service.UsuarioService;
import com.example.backend.service.TiendaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;
import java.util.List;

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
        Pedido p = new Pedido();
        p.setTipoEntrega(dto.getTipoEntrega());
        p.setMetodoPago(dto.getMetodoPago());
        p.setDireccionEntrega(dto.getDireccionEntrega());
        if (dto.getUsuarioId() != null) p.setUsuario(usuarioService.findById(dto.getUsuarioId()));
        if (dto.getTiendaId() != null) p.setTienda(tiendaService.findById(dto.getTiendaId()));
        List<ItemPedido> items = dto.getItems().stream().map(it -> {
            Producto prod = productoService.findById(it.getProductoId());
            ItemPedido ip = ItemPedido.builder()
                    .producto(prod)
                    .cantidad(it.getCantidad())
                    .precioUnitario(it.getPrecioUnitario())
                    .build();
            return ip;
        }).collect(Collectors.toList());
        p.setItems(items);
        Pedido saved = pedidoService.create(p);
        dto.setId(saved.getId());
        dto.setFechaPedido(saved.getFechaPedido());
        dto.setEstado(saved.getEstado().name());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listByUsuario(@PathVariable Long usuarioId) {
        List<PedidoDTO> out = pedidoService.listByUsuario(usuarioId).stream().map(p -> PedidoDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario() != null ? p.getUsuario().getId() : null)
                .tiendaId(p.getTienda() != null ? p.getTienda().getId() : null)
                .fechaPedido(p.getFechaPedido())
                .estado(p.getEstado().name())
                .total(p.getTotal())
                .direccionEntrega(p.getDireccionEntrega())
                .items(p.getItems().stream().map(i -> ItemPedidoDTO.builder()
                        .productoId(i.getProducto() != null ? i.getProducto().getId() : null)
                        .cantidad(i.getCantidad())
                        .precioUnitario(i.getPrecioUnitario())
                        .build()).collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getById(@PathVariable Long id) {
        Pedido p = pedidoService.findById(id);
        PedidoDTO dto = PedidoDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario() != null ? p.getUsuario().getId() : null)
                .tiendaId(p.getTienda() != null ? p.getTienda().getId() : null)
                .fechaPedido(p.getFechaPedido())
                .estado(p.getEstado().name())
                .total(p.getTotal())
                .direccionEntrega(p.getDireccionEntrega())
                .items(p.getItems().stream().map(i -> ItemPedidoDTO.builder()
                        .productoId(i.getProducto() != null ? i.getProducto().getId() : null)
                        .cantidad(i.getCantidad())
                        .precioUnitario(i.getPrecioUnitario())
                        .build()).collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> updateEstado(@PathVariable Long id, @RequestParam String estado) {
        Pedido.EstadoPedido est = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
        pedidoService.updateEstado(id, est);
        return ResponseEntity.noContent().build();
    }
}