package com.example.backend.service;

import com.example.backend.model.Producto;
import com.example.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public Producto create(Producto p) {
        return repo.save(p);
    }

    public Producto update(Long id, Producto cambios) {
        Producto prod = repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (cambios.getNombre() != null) prod.setNombre(cambios.getNombre());
        if (cambios.getPrecio() != null) prod.setPrecio(cambios.getPrecio());
        if (cambios.getDescripcion() != null) prod.setDescripcion(cambios.getDescripcion());
        if (cambios.getStock() != null) prod.setStock(cambios.getStock());
        if (cambios.getActivo() != null) prod.setActivo(cambios.getActivo());
        return repo.save(prod);
    }

    public Producto findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Producto> listAll() {
        return repo.findAll();
    }

    public List<Producto> listByTienda(Long tiendaId) {
        return repo.findByTiendaId(tiendaId);
    }

    public List<Producto> listByCategoria(String categoria) {
        return repo.findByCategoriaIgnoreCase(categoria);
    }

    public List<Producto> searchByName(String q) {
        return repo.findByNombreContainingIgnoreCase(q);
    }

    public void decreaseStock(Long productoId, int cantidad) {
        Producto p = repo.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        int stock = p.getStock() == null ? 0 : p.getStock();
        if (stock < cantidad) throw new RuntimeException("Stock insuficiente");
        p.setStock(stock - cantidad);
        repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}