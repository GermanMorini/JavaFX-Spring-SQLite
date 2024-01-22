package com.javafx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Domicilio {
      private Integer numero;
      private Integer edificio_manzana;
      private Integer piso_lote;
      private Integer departamento;
      private String calle_1;
      private String calle_2;
      private String barrio;
      private Integer codigo_postal;
      private String pais;
      private String provincia;
      private String localidad;
}
