package com.javafx.service;

import com.javafx.model.Alumno;
import com.javafx.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlumnoService {

      @Autowired private AlumnoRepository repo;

      public List<Alumno> getAll() {
            return repo.findAll();
      }

      public Optional<Alumno> getById(Long id) {
            return repo.findById(id);
      }

      public void save(Alumno e) {
            repo.save(e);
      }

      public void delete(Alumno em) {
            repo.deleteById(em.getId());
      }

      public void deleteAllInList(List<Alumno> em) {
            repo.deleteAllById(
                    em.stream()
                            .map(Alumno::getId)
                            .toList()
            );
      }
}
