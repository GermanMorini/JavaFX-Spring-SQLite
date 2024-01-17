package com.javafx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// Una entidad es un objeto/condepto de la realidad que puede ser identificado y almacenado en una base de datos
@Entity @Data
@Table(name = "materias")
@AllArgsConstructor
@NoArgsConstructor
public class Materia {

//	Estos se dicen que son los atributos de la entidad
      @Id private UUID id;
      @Column private String nombre;
      @Column private Date fecha_inicio;
      @Column private Character catedra;

//      'ManyToMany' indica que una materia tiene varios alumnos, y un alumno tiene varias materias (ver 'Alumnos')
//	Esto se llama relación, que indica como se asocian las entidades (en este caso Materia-Alumno)
//      'FetchType' indica que se deberán cargar (en la misma sesión) todos los alumnos correspondientes
      @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable( // Esta anotación no hace falta, es en caso de ya tener armada la base de datos
              name = "inscripciones", // nombre de la tabla que relaciona alumnos-materias
              joinColumns = @JoinColumn(name = "materia_id"), // nombre de la col. que referencia a la materia
              inverseJoinColumns = @JoinColumn(name = "alumno_id") // nombre de la col. que designa un al. a una mat.
      )
      private List<Alumno> alumnos;
//      Referencia; https://www.baeldung.com/jpa-many-to-many

      @Override
      public String toString() {
            return "%s (%s)".formatted(nombre, catedra);
      }
}
