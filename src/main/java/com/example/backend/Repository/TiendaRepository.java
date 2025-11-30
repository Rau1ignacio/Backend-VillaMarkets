package com.example.backend.repository;

import com.example.backend.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    List<Tienda> findByVendedorId(Long vendedorId);
}