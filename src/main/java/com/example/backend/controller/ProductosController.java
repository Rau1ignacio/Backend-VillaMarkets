
package com.example.backend.controller;

import com.example.backend.Service.ProductosService;
import com.example.backend.model.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    private final ProductosService productosService;

    public ProductosController(@Autowired ProductosService productosService) {
        this.productosService = productosService;
    }

    @GetMapping
    public ResponseEntity<List<Productos>> getAllProductos() {
        List<Productos> productos = productosService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> getProductoById(@PathVariable long id) {
        Productos producto = productosService.getProductoById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Productos> createProducto(@RequestBody Productos productos) {
        Productos savedProducto = productosService.saveProducto(productos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> update(@PathVariable long id, @RequestBody Productos productos) {
        Productos existingProducto = productosService.getProductoById(id);
        if (existingProducto != null) {
            productos.setIdProducto(id);
            Productos updatedProducto = productosService.saveProducto(productos);
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Productos existingProducto = productosService.getProductoById(id);
        if (existingProducto == null) {
            return ResponseEntity.notFound().build();
        }
        productosService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Productos>> findByCategoria(@PathVariable String categoria) {
        List<Productos> productos = productosService.getAllProductos()
                .stream()
                .filter(p -> categoria.equalsIgnoreCase(p.getCategoriaProducto()))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(productos);
    }
}