package com.example.backend.controller;

import com.example.backend.model.Usuarios;
import com.example.backend.Service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(@Autowired UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> getAll() {
        return ResponseEntity.ok(usuariosService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getBYid(@PathVariable long id) {
       return usuariosService.getUsuarioById(id) != null 
                ? ResponseEntity.ok(usuariosService.getUsuarioById(id))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Usuarios> createUsuario(@RequestBody Usuarios usuario) {
        Usuarios savedUsuario = usuariosService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> update(@PathVariable long id, @RequestBody Usuarios usuario) {
        Usuarios existingUsuario = usuariosService.getUsuarioById(id);
        if (existingUsuario != null) {
            usuario.setIdUsuario(id);
            Usuarios updatedUsuario = usuariosService.saveUsuario(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Usuarios existing = usuariosService.getUsuarioById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        usuariosService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}