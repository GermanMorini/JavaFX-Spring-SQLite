package com.javafx.services;

import com.javafx.models.Proyecto;
import com.javafx.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProyectoService {

//	Las dependencias inyectadas con @Autowired son únicas
//	La instancia que se genera es única para cualquier clase donde se inyecte
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

      public void deleteProyecto(Proyecto p) {
            repo.deleteById(p.getId().toString());
      }

      public void deleteAllById(List<Proyecto> p) {
            repo.deleteAllById(p.stream()
                    .map(Proyecto::getId)
                    .map(Objects::toString)
                    .toList()
            );
      }
}
