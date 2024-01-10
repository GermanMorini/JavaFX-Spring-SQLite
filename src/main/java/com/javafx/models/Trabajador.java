package com.javafx.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class Trabajador {

      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Id private Long id;
      @Column private String nombre;
      @Column private String apellido;
      @Column private Integer dni;
      @Column private Integer edad;

      @Override
      public String toString() {
            return nombre;
      }
}
