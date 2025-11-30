package com.example.backend.repository;

import com.example.backend.model.RegistroCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroCompraRepository extends JpaRepository<RegistroCompra, Long> {
    List<RegistroCompra> findByUsuarioId(Long usuarioId);
}