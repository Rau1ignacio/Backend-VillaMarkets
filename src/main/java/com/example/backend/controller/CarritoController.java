package com.example.backend.controller;

import com.example.backend.dto.CarritoDTO;
import com.example.backend.dto.ItemCarritoDTO;
import com.example.backend.model.Carrito;
import com.example.backend.model.ItemCarrito;
import com.example.backend.model.Producto;
import com.example.backend.service.CarritoService;
import com.example.backend.service.ProductoService;
import com.example.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(toDTO(c));
    }

    @PostMapping("/usuario/{usuarioId}/add")
    public ResponseEntity<CarritoDTO> addItem(@PathVariable Long usuarioId, @RequestBody ItemCarritoDTO itemDto) {
        Carrito c = carritoService.addItem(usuarioId, itemDto.getProductoId(), itemDto.getCantidad());
        return ResponseEntity.ok(toDTO(c));
    }

    @DeleteMapping("/{carritoId}/item/{itemId}")
    public ResponseEntity<CarritoDTO> removeItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        Carrito c = carritoService.removeItem(carritoId, itemId);
        return ResponseEntity.ok(toDTO(c));
    }

    @DeleteMapping("/{carritoId}/clear")
    public ResponseEntity<Void> clear(@PathVariable Long carritoId) {
        carritoService.clearCart(carritoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{carritoId}/item/{itemId}")
    public ResponseEntity<CarritoDTO> updateCantidad(@PathVariable Long carritoId,
                                                     @PathVariable Long itemId,
                                                     @RequestBody ItemCarritoDTO dto) {
        Carrito carrito = carritoService.updateItemQuantity(carritoId, itemId, dto.getCantidad());
        return ResponseEntity.ok(toDTO(carrito));
    }

    private CarritoDTO toDTO(Carrito carrito) {
        return CarritoDTO.builder()
                .id(carrito.getId())
                .usuarioId(carrito.getUsuario() != null ? carrito.getUsuario().getId() : null)
                .items(carrito.getItems() == null ? null : carrito.getItems().stream()
                        .map(this::toItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private ItemCarritoDTO toItemDTO(ItemCarrito item) {
        Producto producto = item.getProducto();
        return ItemCarritoDTO.builder()
                .id(item.getId())
                .productoId(producto != null ? producto.getId() : null)
                .productoNombre(producto != null ? producto.getNombre() : null)
                .productoImagen(producto != null ? producto.getImagen() : null)
                .tiendaId(producto != null && producto.getTienda() != null ? producto.getTienda().getId() : null)
                .tiendaNombre(producto != null && producto.getTienda() != null ? producto.getTienda().getNombre() : null)
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .build();
    }
}
