package com.example.backend.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String apellidos;
    @Column(unique = true)
    private String rut;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String correo;
    private String telefono;
    private String direccion;
    private String rol; // e.g. "admin" | "cliente" | "vendedor"

    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tienda> tiendas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Carrito carrito;
}