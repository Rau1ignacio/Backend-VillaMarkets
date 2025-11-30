package com.example.backend.controller;

import com.example.backend.model.Minimarkets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.Service.MinimarketsService;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/minimarkets")
@CrossOrigin(origins = "*")
public class MinimarketsController {

    private final MinimarketsService minimarketsService;

    public MinimarketsController(@Autowired MinimarketsService minimarketsService) {
        this.minimarketsService = minimarketsService;
    }

    @GetMapping
    public ResponseEntity<List<Minimarkets>> getAllMinimarkets() {
        List<Minimarkets> minimarkets = minimarketsService.findAll();
        return ResponseEntity.ok(minimarkets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Minimarkets> getMinimarketById(@PathVariable Long id) {
        return minimarketsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Minimarkets> createMinimarket(@RequestBody Minimarkets minimarket) {
        Minimarkets savedMinimarket = minimarketsService.save(minimarket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMinimarket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Minimarkets> update(@PathVariable Long id, @RequestBody Minimarkets minimarket) {
        if (!minimarketsService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        minimarket.setIdMinimarket(id);
        Minimarkets updatedMinimarket = minimarketsService.save(minimarket);
        return ResponseEntity.ok(updatedMinimarket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!minimarketsService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        minimarketsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}