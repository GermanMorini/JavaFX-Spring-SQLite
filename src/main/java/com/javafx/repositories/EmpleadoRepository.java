package com.javafx.repositories;

import com.javafx.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
      @Override
      void deleteById(Long aLong);

      @Override
      void deleteAllById(Iterable<? extends Long> longs);
}
