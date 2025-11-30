package com.example.backend.Repository;

import com.example.backend.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    
    Usuarios findByUsername(String username);

    boolean existsByUsername(String username);

    List<Usuarios> findByRole(String role);

    List<Usuarios> findByCorreoElectronico(String correoElectronico);
}