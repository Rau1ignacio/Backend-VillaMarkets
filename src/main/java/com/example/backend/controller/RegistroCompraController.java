package com.example.backend.controller;

import com.example.backend.Service.RegistrocompraService;
import com.example.backend.model.Registrocompra;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrocompra")
@CrossOrigin(origins = "*")
public class RegistroCompraController {

    @Autowired
    private RegistrocompraService registrocompraService;

    // Obtener todos los registros de compra
    @GetMapping
    public List<Registrocompra> getAllRegistros() {
        return registrocompraService.getAllRegistros();
    }

    // Obtener registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Registrocompra> getRegistroById(@PathVariable Long id) {
        return registrocompraService.getRegistroById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo registro de compra
    @PostMapping
    public Registrocompra createRegistro(@RequestBody Registrocompra registrocompra) {
        return registrocompraService.createRegistro(registrocompra);
    }

    // Actualizar registro de compra
    @PutMapping("/{id}")
    public ResponseEntity<Registrocompra> updateRegistro(@PathVariable Long id, @RequestBody Registrocompra registroDetails) {
        try {
            Registrocompra updatedRegistro = registrocompraService.updateRegistro(id, registroDetails);
            return ResponseEntity.ok(updatedRegistro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar registro de compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {
        registrocompraService.deleteRegistro(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar registros por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public List<Registrocompra> getRegistrosByUsuarioId(@PathVariable Long idUsuario) {
        return registrocompraService.getRegistrosByUsuarioId(idUsuario);
    }


}