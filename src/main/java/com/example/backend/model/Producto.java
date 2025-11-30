package com.example.backend.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String categoria;

    private Integer stock;

    private String imagen; // URL o path

    private Boolean activo = true;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    @JsonIgnore
    private Tienda tienda;
}