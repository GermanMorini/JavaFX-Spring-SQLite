package com.javafx.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Data
@Table(name = "proyectos")
@AllArgsConstructor
@NoArgsConstructor
public class Proyecto {

      @Id private Long id;
      @Column(unique = true) private String nombre;
      @Column private Date fecha_inicio;
      @Column private Boolean finalizado;
      @OneToMany(cascade = CascadeType.ALL) private List<Empleado> empleados = new ArrayList<>();

      @Override
      public String toString() {
            return nombre;
      }
}
