package com.javafx.models;

import com.javafx.controllers.ProyectoFormController;
import com.javafx.controllers.ProyectoTableController;
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

//      'ManyToMany' indica que un proyecto tiene varios empleados, y un empleados tiene varios proyectos (ver 'Empleados')
//      'FetchType' indica que se deberán cargar (en la misma sesión) todos los empleados correspondientes
      @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable(
              name = "empleados_designados",
              joinColumns = @JoinColumn(name = "proyecto_id"),
              inverseJoinColumns = @JoinColumn(name = "empleado_id")
      )
      private List<Empleado> empleados;
//      Referencia; https://www.baeldung.com/jpa-many-to-many

      @Override
      public String toString() {
            return nombre;
      }
}
