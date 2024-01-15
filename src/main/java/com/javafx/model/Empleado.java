package com.javafx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Id private Long id;
      @Column private String nombre;
      @Column private String apellido;
      @Column private Integer edad;
      @Column(unique = true) private Integer dni; // por razones de seguridad, en la BD se encripta toda la información sensible
      @Column private Long telefono;
      @Column private String email;
//      'mappedBy' es el nombre del campo en la entidad referenciada
      @ManyToMany(fetch = FetchType.EAGER, mappedBy = "empleados")
      private List<Proyecto> proyectos;
//      Referencia: https://www.baeldung.com/jpa-many-to-many

      @Override
      public String toString() {
            return nombre;
      }
}
