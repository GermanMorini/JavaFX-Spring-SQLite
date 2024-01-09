package com.javafx.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Proyecto {

      private String id;
      private String nombre;
      private Date fecha_inicio;
      private Boolean finalizado;
      private List<Trabajador> trabajadores;
}
