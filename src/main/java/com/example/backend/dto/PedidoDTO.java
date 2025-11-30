package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private Long tiendaId;
    private LocalDateTime fechaPedido;
    private String estado;
    private String tipoEntrega;
    private String metodoPago;
    private BigDecimal total;
    private String direccionEntrega;
    private List<ItemPedidoDTO> items;
}