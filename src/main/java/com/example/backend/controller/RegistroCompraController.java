package com.example.backend.controller;

import com.example.backend.dto.RegistroCompraDTO;
import com.example.backend.model.RegistroCompra;
import com.example.backend.model.Usuario;
import com.example.backend.service.RegistroCompraService;
import com.example.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrocompra")
@CrossOrigin(origins = "*")
public class RegistroCompraController {

    private final RegistroCompraService service;
    private final UsuarioService usuarioService;

    public RegistroCompraController(RegistroCompraService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroCompraDTO>> listAll() {
        List<RegistroCompraDTO> out = service.listAll().stream().map(r -> RegistroCompraDTO.builder()
                .id(r.getId())
                .usuarioId(r.getUsuario() != null ? r.getUsuario().getId() : null)
                .nombres(r.getNombres())
                .apellidos(r.getApellidos())
                .rut(r.getRut())
                .dv(r.getDv())
                .fechaCompra(r.getFechaCompra())
                .montoTotal(r.getMontoTotal())
                .detalleCompra(r.getDetalleCompra())
                .cantidadProductos(r.getCantidadProductos())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @PostMapping
    public ResponseEntity<RegistroCompraDTO> create(@RequestBody RegistroCompraDTO dto) {
        RegistroCompra r = RegistroCompra.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .rut(dto.getRut())
                .dv(dto.getDv())
                .detalleCompra(dto.getDetalleCompra())
                .montoTotal(dto.getMontoTotal())
                .cantidadProductos(dto.getCantidadProductos())
                .build();
        if (dto.getUsuarioId() != null) {
            Usuario u = usuarioService.findById(dto.getUsuarioId());
            r.setUsuario(u);
        }
        RegistroCompra saved = service.create(r);
        dto.setId(saved.getId());
        dto.setFechaCompra(saved.getFechaCompra());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroCompraDTO>> byUsuario(@PathVariable Long usuarioId) {
        List<RegistroCompraDTO> out = service.listByUsuario(usuarioId).stream().map(r -> RegistroCompraDTO.builder()
                .id(r.getId())
                .usuarioId(r.getUsuario() != null ? r.getUsuario().getId() : null)
                .nombres(r.getNombres())
                .apellidos(r.getApellidos())
                .rut(r.getRut())
                .dv(r.getDv())
                .fechaCompra(r.getFechaCompra())
                .montoTotal(r.getMontoTotal())
                .detalleCompra(r.getDetalleCompra())
                .cantidadProductos(r.getCantidadProductos())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}