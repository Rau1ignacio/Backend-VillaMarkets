package com.example.backend.controller;

import com.example.backend.dto.TiendaDTO;
import com.example.backend.model.Tienda;
import com.example.backend.model.Usuario;
import com.example.backend.service.TiendaService;
import com.example.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/minimarkets")
@CrossOrigin(origins = "*")
public class TiendaController {

    private final TiendaService service;
    private final UsuarioService usuarioService;

    public TiendaController(TiendaService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<TiendaDTO>> list(@RequestParam(value = "vendedorId", required = false) Long vendedorId) {
        List<Tienda> tiendas = vendedorId != null ? service.listByVendedor(vendedorId) : service.listAll();

        List<TiendaDTO> out = tiendas.stream().map(t -> TiendaDTO.builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .direccion(t.getDireccion())
                .region(t.getRegion())
                .comuna(t.getComuna())
                .horario(t.getHorario())
                .latitud(t.getLatitud())
                .longitud(t.getLongitud())
                .vendedorId(t.getVendedor() != null ? t.getVendedor().getId() : null)
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @PostMapping
    public ResponseEntity<TiendaDTO> create(@RequestBody TiendaDTO dto) {
        Tienda t = Tienda.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .region(dto.getRegion())
                .comuna(dto.getComuna())
                .horario(dto.getHorario())
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .build();
        if (dto.getVendedorId() != null) {
            Usuario u = usuarioService.findById(dto.getVendedorId());
            t.setVendedor(u);
        }
        Tienda saved = service.create(t);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> getById(@PathVariable Long id) {
        Tienda t = service.findById(id);
        TiendaDTO dto = TiendaDTO.builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .direccion(t.getDireccion())
                .region(t.getRegion())
                .comuna(t.getComuna())
                .horario(t.getHorario())
                .latitud(t.getLatitud())
                .longitud(t.getLongitud())
                .vendedorId(t.getVendedor() != null ? t.getVendedor().getId() : null)
                .build();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiendaDTO> update(@PathVariable Long id, @RequestBody TiendaDTO dto) {
        Tienda cambios = new Tienda();
        cambios.setNombre(dto.getNombre());
        cambios.setDireccion(dto.getDireccion());
        cambios.setHorario(dto.getHorario());
        Tienda updated = service.update(id, cambios);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
