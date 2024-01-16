package com.javafx.service;

import com.javafx.model.Materia;
import com.javafx.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MateriaService {

//	Las dependencias inyectadas con @Autowired son únicas
//	La instancia que se genera es única para cualquier clase donde se inyecte
      @Autowired private MateriaRepository repo;

      public List<Materia> getAll() {
            return repo.findAll();
      }

      public Optional<Materia> getMateria(String id) {
            return repo.findById(id);
      }

      public void saveMateria(Materia mat) {
            repo.save(mat);
      }

      public void deleteMateria(Materia mat) {
            repo.deleteById(mat.getId().toString());
      }

      public void deleteAllById(List<Materia> mat) {
            repo.deleteAllById(mat.stream()
                    .map(Materia::getId)
                    .map(Objects::toString)
                    .toList()
            );
      }
}
