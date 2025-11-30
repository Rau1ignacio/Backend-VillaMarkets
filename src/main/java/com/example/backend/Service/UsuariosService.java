package com.example.backend.Service;

import com.example.backend.Repository.UsuariosRepository;
import com.example.backend.model.Usuarios;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Usuarios saveUsuario(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    public Usuarios getUsuarioById(long id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    public void deleteUsuario(long id) {
        usuariosRepository.deleteById(id);
    }

    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
    }
} 

