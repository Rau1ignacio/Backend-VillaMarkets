package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoDTO {
    private Long productoId;
    private String productoNombre;
    private String imagenProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
