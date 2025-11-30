package com.example.backend.service;

import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuariosRepository usuarioRepository;

    public UsuarioService(UsuariosRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario create(Usuario u) {
        u.setFechaCreacion(LocalDateTime.now());
        // TODO: hashear password antes de guardar (implementar PasswordEncoder)
        return usuarioRepository.save(u);
    }

    public Usuario update(Long id, Usuario cambios) {
        Usuario existe = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (cambios.getNombres() != null) existe.setNombres(cambios.getNombres());
        if (cambios.getApellidos() != null) existe.setApellidos(cambios.getApellidos());
        if (cambios.getDireccion() != null) existe.setDireccion(cambios.getDireccion());
        if (cambios.getTelefono() != null) existe.setTelefono(cambios.getTelefono());
        // evitar actualizar password sin proceso separado
        return usuarioRepository.save(existe);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public Usuario login(String username, String password) {
        // Buscar por username o correo
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseGet(() -> usuarioRepository.findByCorreo(username).orElse(null));
        
        if (u != null && u.getPassword() != null && u.getPassword().equals(password)) {
            return u;
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}