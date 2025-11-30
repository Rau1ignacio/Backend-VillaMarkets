
package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "precio_producto")
    private double precioProducto;

    @Column(name = "stock_producto")
    private int stockProducto;

    @Column(name = "categoria_producto")
    private String categoriaProducto;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "stock")
    private int stock;

}

