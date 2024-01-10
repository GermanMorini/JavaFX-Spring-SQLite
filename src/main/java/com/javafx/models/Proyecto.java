package com.javafx.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proyectos")
public class Proyecto {

      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Id private Long id;
      @Column private String nombre;
      @Column private Date fecha_inicio;
      @Column private Boolean finalizado;
      @OneToMany private List<Trabajador> trabajadores;

      @Override
      public String toString() {
            return nombre;
      }
}
