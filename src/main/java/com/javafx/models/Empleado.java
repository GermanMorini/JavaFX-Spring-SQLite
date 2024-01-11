package com.javafx.models;

import com.javafx.controllers.EmpleadoFormController;
import com.javafx.controllers.EmpleadoTableController;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
// Un listener es una clase que realiza acciones cuando ocurren cambios en la base de datos
public class Empleado {

      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Id private Long id;
      @Column private String nombre;
      @Column private String apellido;
      @Column(unique = true) private Integer dni;
      @Column private Integer edad;

      @Override
      public String toString() {
            return nombre;
      }
}
