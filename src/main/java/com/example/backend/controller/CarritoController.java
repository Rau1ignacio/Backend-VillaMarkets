package com.example.backend.controller;

import com.example.backend.dto.CarritoDTO;
import com.example.backend.dto.ItemCarritoDTO;
import com.example.backend.model.Carrito;
import com.example.backend.model.ItemCarrito;
import com.example.backend.model.Producto;
import com.example.backend.service.CarritoService;
import com.example.backend.service.ProductoService;
import com.example.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carritos")
@CrossOrigin(origins = "http://localhost:5173")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public CarritoController(CarritoService carritoService, ProductoService productoService, UsuarioService usuarioService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoDTO> getByUsuario(@PathVariable Long usuarioId) {
        Carrito c = carritoService.getOrCreateByUsuarioId(usuarioId);
        CarritoDTO dto = CarritoDTO.builder()
                .id(c.getId())
                .usuarioId(c.getUsuario() != null ? c.getUsuario().getId() : null)
                .items(c.getItems() == null ? null : c.getItems().stream().map(it -> ItemCarritoDTO.builder()
                        .id(it.getId())
                        .productoId(it.getProducto() != null ? it.getProducto().getId() : null)
                        .cantidad(it.getCantidad())
                        .precioUnitario(it.getPrecioUnitario())
                        .build()).collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/usuario/{usuarioId}/add")
    public ResponseEntity<CarritoDTO> addItem(@PathVariable Long usuarioId, @RequestBody ItemCarritoDTO itemDto) {
        Carrito c = carritoService.addItem(usuarioId, itemDto.getProductoId(), itemDto.getCantidad());
        return getByUsuario(usuarioId);
    }

    @DeleteMapping("/{carritoId}/item/{itemId}")
    public ResponseEntity<CarritoDTO> removeItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        carritoService.removeItem(carritoId, itemId);
        // find carrito and return
        Carrito c = carritoService.getOrCreateByUsuarioId(null); // placeholder: ideally buscar por carritoId service
        CarritoDTO dto = CarritoDTO.builder().id(c.getId()).build();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{carritoId}/clear")
    public ResponseEntity<Void> clear(@PathVariable Long carritoId) {
        carritoService.clearCart(carritoId);
        return ResponseEntity.noContent().build();
    }
}