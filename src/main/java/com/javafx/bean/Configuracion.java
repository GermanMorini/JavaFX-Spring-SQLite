package com.javafx.bean;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Random;

@Configuration
public class Configuracion {
//    Bean es un elemento de spring que permite inyectar dependencias
    @Bean
    @Qualifier("generador-id-alumnos")
    public IdentifierGenerator getID() {
        Random r = new Random();
        return (s, o) -> r.nextInt(1000000,9999999);
    }

//    Este bean permite configurar la fuente de datos para que pueda usar el archivo de resources
//    Se puede hacer desde 'aplication.properties' también
//    (En general no es necesario)
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName("org.sqlite.JDBC");
        builder.url("jdbc:sqlite:data.sqlite");
        return builder.build();
    }
}
