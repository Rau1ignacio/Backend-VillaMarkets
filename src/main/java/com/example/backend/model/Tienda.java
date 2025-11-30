package com.example.backend.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tiendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String region;
    private String comuna;
    private String horario;
    private Double latitud;
    private Double longitud;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Producto> productos;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;
}