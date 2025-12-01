package com.example.backend.controller;

import com.example.backend.dto.ProductoDTO;
import com.example.backend.model.Producto;
import com.example.backend.model.Tienda;
import com.example.backend.service.ProductoService;
import com.example.backend.service.TiendaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;
    private final TiendaService tiendaService;

    public ProductoController(ProductoService productoService, TiendaService tiendaService) {
        this.productoService = productoService;
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listAll() {
        List<ProductoDTO> list = productoService.listAll().stream().map(p -> ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .descripcion(p.getDescripcion())
                .categoria(p.getCategoria())
                .stock(p.getStock())
                .imagen(p.getImagen())
                .activo(p.getActivo())
                .tiendaId(p.getTienda() != null ? p.getTienda().getId() : null)
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(@PathVariable Long id) {
        Producto p = productoService.findById(id);
        ProductoDTO dto = ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .descripcion(p.getDescripcion())
                .categoria(p.getCategoria())
                .stock(p.getStock())
                .imagen(p.getImagen())
                .activo(p.getActivo())
                .tiendaId(p.getTienda() != null ? p.getTienda().getId() : null)
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<List<ProductoDTO>> byTienda(@PathVariable Long tiendaId) {
        List<ProductoDTO> list = productoService.listByTienda(tiendaId).stream().map(p -> ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .descripcion(p.getDescripcion())
                .categoria(p.getCategoria())
                .stock(p.getStock())
                .imagen(p.getImagen())
                .activo(p.getActivo())
                .tiendaId(tiendaId)
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ProductoDTO>> byAdmin(@PathVariable Long adminId) {
        List<ProductoDTO> list = productoService.listByAdministrador(adminId).stream().map(p -> ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .descripcion(p.getDescripcion())
                .categoria(p.getCategoria())
                .stock(p.getStock())
                .imagen(p.getImagen())
                .activo(p.getActivo())
                .tiendaId(p.getTienda() != null ? p.getTienda().getId() : null)
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO dto) {
        Producto p = Producto.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .stock(dto.getStock())
                .imagen(dto.getImagen())
                .activo(dto.getActivo() == null ? true : dto.getActivo())
                .build();
        if (dto.getTiendaId() != null) {
            Tienda t = tiendaService.findById(dto.getTiendaId());
            p.setTienda(t);
        }
        Producto saved = productoService.create(p);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        Producto cambios = new Producto();
        cambios.setNombre(dto.getNombre());
        cambios.setPrecio(dto.getPrecio());
        cambios.setDescripcion(dto.getDescripcion());
        cambios.setStock(dto.getStock());
        cambios.setActivo(dto.getActivo());
        Producto updated = productoService.update(id, cambios);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
