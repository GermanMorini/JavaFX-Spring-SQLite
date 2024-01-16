package com.javafx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity @Data
@Table(name = "proyectos")
@AllArgsConstructor
@NoArgsConstructor
public class Proyecto {

      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Id private Long id;
      @Column(unique = true) private String nombre;
      @Column private Date fecha_inicio;
      @Column private Boolean finalizado;

//      'ManyToMany' indica que un proyecto tiene varios empleados, y un empleado tiene varios proyectos (ver 'Empleados')
//      'FetchType' indica que se deberán cargar (en la misma sesión) todos los empleados correspondientes
      @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable( // Esta anotación no hace falta, es en caso de ya tener armada la base de datos
              name = "empleados_designados", // nombre de la tabla que relaciona proyectos-empleados
              joinColumns = @JoinColumn(name = "proyecto_id"), // nombre de la col. que referencia al proyecto
              inverseJoinColumns = @JoinColumn(name = "empleado_id") // nombre de la col. que designa un emp. a un proy.
      )
      private List<Empleado> empleados;
//      Referencia; https://www.baeldung.com/jpa-many-to-many

      @Override
      public String toString() {
            return nombre;
      }
}
