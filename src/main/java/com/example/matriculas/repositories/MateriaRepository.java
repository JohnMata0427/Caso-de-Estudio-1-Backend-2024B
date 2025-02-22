package com.example.matriculas.repositories;

import com.example.matriculas.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
  boolean existsByCodigo(String codigo);
}