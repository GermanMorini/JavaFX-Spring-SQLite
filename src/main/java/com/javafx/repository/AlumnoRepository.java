package com.javafx.repository;

import com.javafx.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
      @Override
      void deleteById(Long aLong);

      @Override
      void deleteAllById(Iterable<? extends Long> longs);
}
