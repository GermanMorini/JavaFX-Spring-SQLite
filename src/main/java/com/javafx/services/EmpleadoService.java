package com.javafx.services;

import com.javafx.models.Empleado;
import com.javafx.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoService {

      @Autowired private EmpleadoRepository repo;

      public List<Empleado> getAll() {
            return repo.findAll();
      }

      public Optional<Empleado> getById(Long id) {
            return repo.findById(id);
      }

      public void save(Empleado e) {
            repo.save(e);
      }

      public void delete(Empleado em) {
            repo.deleteById(em.getId());
      }

      public void deleteAllInList(List<Empleado> em) {
            repo.deleteAllById(
                    em.stream()
                            .map(Empleado::getId)
                            .toList()
            );
      }
}
