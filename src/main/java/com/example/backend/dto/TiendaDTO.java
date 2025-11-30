package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiendaDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String region;
    private String comuna;
    private String horario;
    private Double latitud;
    private Double longitud;
    private Long vendedorId;
}