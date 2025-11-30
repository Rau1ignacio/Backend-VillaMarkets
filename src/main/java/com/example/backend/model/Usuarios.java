
// infornacion de usuarios
// id de usuario
// nombres de usuario
// apellidos paterno
// apellidos materno
// rut de usuario
// dv de rut
// correo electronico
// telefono de contacto
// direccion de usuario
/// region de usuario
/// comuna de usuario
// fecha de creacion
// comida elegida 

package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuarios {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private long idUsuario;
@Column(name = "nombres_usuario")

private String nombresUsuario;
@Column(name = "apellido_paterno")
private String apellidoPaterno;

@Column(name = "apellido_materno")
private String apellidoMaterno;

@Column(name = "rut_usuario")
private String rutUsuario;
@Column(name = "dv_rut")
private String dvRut;

@Column(name = "role")
private String role;
@Column(name = "username")
private String username;

@Column(name = "password")
private String password;


@Column(name = "correo_electronico")
private String correoElectronico;

@Column(name = "telefono_contacto")
private String telefonoContacto;

@Column(name = "direccion_usuario")
private String direccionUsuario;

@Column(name = "region_usuario")
private String regionUsuario;

@Column(name = "comuna_usuario")
private String comunaUsuario;
@Column(name = "fecha_creacion")
private Date fechaCreacion;

@Column(name = "comida_elegida")
private String comidaElegida;



}

