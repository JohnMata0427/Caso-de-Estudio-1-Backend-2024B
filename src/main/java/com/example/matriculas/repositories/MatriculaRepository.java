package com.example.matriculas.repositories;

import com.example.matriculas.models.Matricula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
  List<Matricula> findByEstudianteId(Long estudianteId);
  List<Matricula> findByMateriaId(Long materiaId);
}
