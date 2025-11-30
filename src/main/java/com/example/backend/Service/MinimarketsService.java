package com.example.backend.Service;

import com.example.backend.Repository.MinimarketsRepository;
import com.example.backend.model.Minimarkets;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinimarketsService {

  

    private final MinimarketsRepository repository;

    public MinimarketsService(MinimarketsRepository repository) {
        this.repository = repository;
    }

    public List<Minimarkets> findAll() {
        return repository.findAll();
    }

    public Optional<Minimarkets> findById(Long id) {
        return repository.findById(id);
    }

    public Minimarkets save(Minimarkets minimarket) {
        return repository.save(minimarket);
    }


    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<Minimarkets> findByRegion(String region) {
        return repository.findByRegionMinimarketIgnoreCase(region);
    }

    public List<Minimarkets> findByComuna(String comuna) {
        return repository.findByComunaMinimarketIgnoreCase(comuna);
    }

    public List<Minimarkets> findByNombre(String nombre) {
        return repository.findByNombreMinimarketContainingIgnoreCase(nombre);
    }

}        
    

