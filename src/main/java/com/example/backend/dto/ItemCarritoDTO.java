package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCarritoDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private String productoImagen;
    private Long tiendaId;
    private String tiendaNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
