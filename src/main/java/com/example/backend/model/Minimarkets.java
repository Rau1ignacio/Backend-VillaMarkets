// informacion minimarkets
// id minimarket
// nombre minimarket
// direccion minimarket
// telefono minimarket
// region minimarket
// comuna minimarket

package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "minimarkets")
public class Minimarkets {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long idMinimarket;

@Column(name = "nombre_minimarket")
private String nombreMinimarket;

@Column(name = "direccion_minimarket")
private String direccionMinimarket;

@Column(name = "telefono_minimarket")
private String telefonoMinimarket;

@Column(name = "region_minimarket")
private String regionMinimarket;

@Column(name = "comuna_minimarket")
private String comunaMinimarket;


}
