package com.example.backend.service;

import com.example.backend.model.Tienda;
import com.example.backend.repository.TiendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TiendaService {

    private final TiendaRepository repo;

    public TiendaService(TiendaRepository repo) {
        this.repo = repo;
    }

    public Tienda create(Tienda t) {
        return repo.save(t);
    }

    public Tienda update(Long id, Tienda cambios) {
        Tienda t = repo.findById(id).orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        if (cambios.getNombre() != null) t.setNombre(cambios.getNombre());
        if (cambios.getDireccion() != null) t.setDireccion(cambios.getDireccion());
        return repo.save(t);
    }

    public Tienda findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
    }

    public List<Tienda> listAll() {
        return repo.findAll();
    }

    public List<Tienda> listByVendedor(Long vendedorId) {
        return repo.findByVendedorId(vendedorId);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}