package com.example.backend.Repository;

import com.example.backend.model.Minimarkets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MinimarketsRepository extends JpaRepository<Minimarkets, Long> {

    List<Minimarkets> findByNombreMinimarketContainingIgnoreCase(String nombreMinimarket);
    List<Minimarkets> findByRegionMinimarketIgnoreCase(String region);
    List<Minimarkets> findByComunaMinimarketIgnoreCase(String comuna);
}