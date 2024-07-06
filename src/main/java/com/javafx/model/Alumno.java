package com.javafx.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: hacer una superclase 'Persona' para agrupar los atributos mas importantes para después hacer otras clases (Profesor, ...)
// https://www.baeldung.com/hibernate-inheritance
@Entity @Data
@Table(name = "alumnos")
@NoArgsConstructor
public class Alumno extends Persona {

//    TODO: ver como encriptar esto
//    por razones de seguridad, en la BD se encripta toda la información sensible
    @Column private Long telefono;
    @Column private String email;
//    'mappedBy' es el nombre del campo en la entidad referenciada
//    Referencia: https://www.baeldung.com/jpa-many-to-many
@ManyToMany(fetch = FetchType.EAGER, mappedBy = "alumnos")
private List<Materia> materias;

    public Alumno(Long clave, String nombre, String apellido, Integer edad, Integer dni, Long telefono, String email,
        List<Materia> materias) {
    super(clave, nombre, apellido, edad, dni);
    this.telefono = telefono;
    this.email = email;
    this.materias = materias;
    }

    @Override
    public String toString() {
    return "%s, %s (%d)".formatted(getApellido(), getNombre(), getClave());
    }
}
