package com.example.matriculas.repositories;

import com.example.matriculas.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
  boolean existsByCedula(String cedula);

  boolean existsByEmail(String email);

  boolean existsByTelefono(String telefono);
}
