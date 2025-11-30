package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registrocompra")
    
public class Registrocompra {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long idRegistro;

@Column(name = "id_usuario")
private long idUsuario;
@Column(name = "nombres_usuario")
private String nombresUsuario;

@Column(name = "apellidos_usuario")
private String apellidosUsuario;
@Column(name = "rut_usuario")
private String rutUsuario;

@Column(name = "dv_rut")
private String dvRut;

@Column(name = "fecha_compra")
private Date fechaCompra;

@Column(name = "monto_total")
private double montoTotal;


@Column(name = "detalle_compra")
private String detalleCompra;


@Column(name = "cantidad_productos")
private Integer cantidadProductos;
}