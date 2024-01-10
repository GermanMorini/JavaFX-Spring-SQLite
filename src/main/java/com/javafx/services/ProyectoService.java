package com.javafx.services;

import com.javafx.models.Proyecto;
import com.javafx.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

      @Autowired
      private ProyectoRepository repo;

      public List<Proyecto> getAll() {
            return repo.findAll();
      }

      public Optional<Proyecto> getProyecto(String pname) {
            return repo.findByNombre(pname);
      }

      public void saveProyecto(Proyecto p) {
            repo.save(p);
      }
}
