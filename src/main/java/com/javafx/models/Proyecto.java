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
//      'FetchType' indica que, siempre que se carge una entidad clase 'Proyecto', se deberán cargar (en la misma sesión)
//      todos lalos empleados correspondientes
      @OneToMany(fetch = FetchType.EAGER) private List<Empleado> empleados;

      @Override
      public String toString() {
            return nombre;
      }
}
