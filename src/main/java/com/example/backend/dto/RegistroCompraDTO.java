package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroCompraDTO {
    private Long id;
    private Long usuarioId;
    private String nombres;
    private String apellidos;
    private String rut;
    private String dv;
    private LocalDateTime fechaCompra;
    private BigDecimal montoTotal;
    private String detalleCompra;
    private Integer cantidadProductos;
}