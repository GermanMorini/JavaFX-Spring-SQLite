package com.javafx.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javafx.models.Proyecto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProyectoService {

      private final ObjectMapper om = new ObjectMapper();

      public Proyecto getProyecto(String pname) throws IOException {
            return om.readValue(getClass().getResource("/" + pname + ".json"), Proyecto.class);
      }

      public void saveProyecto(Proyecto p) throws IOException {
            om.writeValue(new File("src/main/resources/" + p.getNombre() + ".json"), p);
      }
}
