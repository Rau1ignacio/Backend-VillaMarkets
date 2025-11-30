package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String categoria;
    private Integer stock;
    private String imagen;
    private Boolean activo;
    private Long tiendaId;
}