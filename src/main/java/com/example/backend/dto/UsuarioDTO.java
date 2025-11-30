package com.example.backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String rut;
    private String username;
    private String password; // solo en request (no devolver en respuestas reales)
    private String correo;
    private String telefono;
    private String direccion;
    private String rol;
    private LocalDateTime fechaCreacion;
}
