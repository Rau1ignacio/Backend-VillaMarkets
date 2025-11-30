package com.example.backend.service;

import com.example.backend.model.RegistroCompra;
import com.example.backend.repository.RegistroCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RegistroCompraService {

    private final RegistroCompraRepository repo;

    public RegistroCompraService(RegistroCompraRepository repo) {
        this.repo = repo;
    }

    public RegistroCompra create(RegistroCompra r) {
        return repo.save(r);
    }

    public List<RegistroCompra> listByUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public List<RegistroCompra> listAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}