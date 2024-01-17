package com.javafx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: hacer una superclase 'Persona' para agrupar los atributos mas importantes para después hacer otras clases (Profesor, ...)
// https://www.baeldung.com/hibernate-inheritance
@Entity @Data
@Table(name = "alumnos")
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {

//      TODO: ver si puedo generar esto con un bean, o de otra forma
      @Id private Long clave;
      @Column(nullable = false) private String nombre;
      @Column(nullable = false) private String apellido;
      @Column(nullable = false) private Integer edad;
//      TODO: ver como encriptar esto
//      por razones de seguridad, en la BD se encripta toda la información sensible
      @Column(nullable = false, unique = true) private Integer dni;
      @Column private Long telefono;
      @Column private String email;
//      'mappedBy' es el nombre del campo en la entidad referenciada
      @ManyToMany(fetch = FetchType.EAGER, mappedBy = "alumnos")
      private List<Materia> materias;
//      Referencia: https://www.baeldung.com/jpa-many-to-many

      @Override
      public String toString() {
            return "%s, %s (%d)".formatted(apellido, nombre, clave);
      }
}
