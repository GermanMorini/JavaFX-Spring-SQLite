package com.javafx.beans;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableTransactionManagement
public class Configuracion {
//      Bean es un elemento de spring que permite inyectar dependencias
      @Bean("saludoLbl")
      public String getSaludo() {
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss - dd/MM/yy");
            return "Te saludo en esta fecha: " + dtf.format(ldt);
      }

//      Este bean permite configurar la fuente de datos para que pueda usar el archivo de resources
//      Se puede hacer desde 'aplication.properties' también
//      (En general no es necesario)
      @Bean
      public DataSource getDataSource() {
            DataSourceBuilder builder = DataSourceBuilder.create();
            builder.driverClassName("org.sqlite.JDBC");
            builder.url("jdbc:sqlite:data.sqlite");
            return builder.build();
      }
}
