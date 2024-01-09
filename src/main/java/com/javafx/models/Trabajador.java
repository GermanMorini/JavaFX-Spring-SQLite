package com.javafx.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Trabajador {

      private Long id;
      private String nombre;
      private String apellido;
      private Integer dni;
      private Integer edad;

      @Override
      public String toString() {
            return nombre;
      }
}
