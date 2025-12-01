package com.example.backend.controller;

import com.example.backend.dto.UsuarioDTO;
import com.example.backend.model.Usuario;
import com.example.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO dto) {
        System.out.println("Registering user: " + dto); // DEBUG LOG
        Usuario u = Usuario.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .rut(dto.getRut())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .rol(dto.getRol())
                .build();
        Usuario saved = service.create(u);
        dto.setId(saved.getId());
        dto.setFechaCreacion(saved.getFechaCreacion());
        dto.setPassword(null);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO dto) {
        Usuario u = service.login(dto.getUsername() != null ? dto.getUsername() : dto.getCorreo(), dto.getPassword());
        UsuarioDTO respuesta = UsuarioDTO.builder()
                .id(u.getId())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .rut(u.getRut())
                .username(u.getUsername())
                .correo(u.getCorreo())
                .telefono(u.getTelefono())
                .direccion(u.getDireccion())
                .rol(u.getRol())
                .fechaCreacion(u.getFechaCreacion())
                .build();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        Usuario u = service.findById(id);
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(u.getId())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .username(u.getUsername())
                .correo(u.getCorreo())
                .telefono(u.getTelefono())
                .direccion(u.getDireccion())
                .rol(u.getRol())
                .fechaCreacion(u.getFechaCreacion())
                .build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() {
        List<UsuarioDTO> list = service.listAll().stream().map(u -> UsuarioDTO.builder()
                .id(u.getId())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .username(u.getUsername())
                .correo(u.getCorreo())
                .telefono(u.getTelefono())
                .direccion(u.getDireccion())
                .rol(u.getRol())
                .fechaCreacion(u.getFechaCreacion())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        Usuario cambios = new Usuario();
        cambios.setNombres(dto.getNombres());
        cambios.setApellidos(dto.getApellidos());
        cambios.setDireccion(dto.getDireccion());
        cambios.setTelefono(dto.getTelefono());
        Usuario updated = service.update(id, cambios);
        dto.setId(updated.getId());
        dto.setPassword(null);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}