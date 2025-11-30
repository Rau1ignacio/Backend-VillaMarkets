package com.example.backend.Repository;

import com.example.backend.model.Registrocompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroCompraRepository extends JpaRepository<Registrocompra, Long> {
    List<Registrocompra> findByIdUsuario(Long idUsuario);
}
