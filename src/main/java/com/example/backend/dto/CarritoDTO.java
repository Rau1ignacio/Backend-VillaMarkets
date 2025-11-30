package com.example.backend.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoDTO {
    private Long id;
    private Long usuarioId;
    private List<ItemCarritoDTO> items;
}