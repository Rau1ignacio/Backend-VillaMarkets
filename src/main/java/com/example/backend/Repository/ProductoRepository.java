package com.example.backend.repository;

import com.example.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTiendaId(Long tiendaId);
    List<Producto> findByTiendaVendedorId(Long vendedorId);
    List<Producto> findByCategoriaIgnoreCase(String categoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByActivoTrue();
}
