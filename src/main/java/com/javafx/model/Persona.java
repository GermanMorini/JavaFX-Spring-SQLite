package com.javafx.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Las entidades que hereden de esta clase heredarán también todos los atributos de esta clase
@MappedSuperclass @Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

//    TODO: ver si puedo generar esto con un bean, o de otra forma
    @Id private Long clave;
    @Column(nullable = false) private String nombre;
    @Column(nullable = false) private String apellido;
    @Column(nullable = false) private Integer edad;
    @Column(nullable = false, unique = true) private Integer dni;
}
