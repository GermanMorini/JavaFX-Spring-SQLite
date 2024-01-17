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

      public Optional<Alumno> getById(Long clave) {
            return repo.findById(clave);
      }

      public void save(Alumno al) {
            repo.save(al);
      }

      public void delete(Alumno al) {
            repo.deleteById(al.getClave());
      }

      public void deleteAllInList(List<Alumno> al) {
            repo.deleteAllById(
                    al.stream()
                            .map(Alumno::getClave)
                            .toList()
            );
      }
}
