package com.javafx.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Configuracion {
      // Bean es un elemento de spring que permite inyectar dependencias
      @Bean("saludoLbl")
      private String getSaludo() {
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss - dd/MM/yy");
            return "Te saludo en esta fecha: " + dtf.format(ldt);
      }
}
