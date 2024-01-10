package com.javafx.repositories;

import com.javafx.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, String> {
      @Override
      List<Proyecto> findAll();

      @Override
      Optional<Proyecto> findById(String s);

      Optional<Proyecto> findByNombre(String n);

      @Override
      <S extends Proyecto> S save(S entity);

      @Override
      void deleteById(String s);

      @Override
      void deleteAllById(Iterable<? extends String> s);
}
