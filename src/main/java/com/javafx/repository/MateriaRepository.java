package com.javafx.repository;

import com.javafx.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, String> {
    @Override
    List<Materia> findAll();

    @Override
    Optional<Materia> findById(String s);

    Optional<Materia> findByNombre(String n);

    @Override
    <S extends Materia> S save(S entity);

    @Override
    void deleteById(String s);

    @Override
    void deleteAllById(Iterable<? extends String> s);
}
