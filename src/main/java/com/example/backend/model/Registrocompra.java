package com.example.backend.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombres;
    private String apellidos;
    private String rut;
    private String dv;

    private LocalDateTime fechaCompra;

    @Column(precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @Column(columnDefinition = "TEXT")
    private String detalleCompra; // JSON o texto resumen

    private Integer cantidadProductos;
}