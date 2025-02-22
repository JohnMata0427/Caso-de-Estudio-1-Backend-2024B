package com.example.matriculas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.matriculas.models.Estudiante;
import com.example.matriculas.models.Materia;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.repositories.MatriculaRepository;
import com.example.matriculas.repositories.EstudianteRepository;
import com.example.matriculas.repositories.MateriaRepository;

@Service
public class MatriculaService {

  @Autowired
  private MatriculaRepository matriculaRepository;

  @Autowired
  private EstudianteRepository estudianteRepository;

  @Autowired
  private MateriaRepository materiaRepository;

  @Transactional(readOnly = true)
  public List<Matricula> obtenerTodasLasMatriculas() {
    return matriculaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerMatriculaPorId(Long id) {
    Matricula matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matricula con id " + id + " no encontrada"));

    Estudiante estudiante = estudianteRepository.findById(matricula.getId_estudiante())
        .orElseThrow(
            () -> new RuntimeException("Estudiante con id " + matricula.getId_estudiante() + " no encontrado"));

    Materia materia = materiaRepository.findById(matricula.getId_materia())
        .orElseThrow(() -> new RuntimeException("Materia con id " + matricula.getId_materia() + " no encontrada"));

    return Optional.of(Map.of("matricula", matricula, "estudiante", estudiante, "materia", materia));
  }

  @Transactional
  public Matricula crearMatricula(Matricula matricula) {

    String codigo = matricula.getCodigo();
    String descripcion = matricula.getDescripcion();
    Long id_estudiante = matricula.getId_estudiante();
    Long id_materia = matricula.getId_materia();

    if (codigo == null || descripcion.isBlank() || id_estudiante == null || id_materia == null)
      throw new RuntimeException("Todos los campos son requeridos");

    if (matriculaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una matricula con el código " + codigo);

    if (matriculaRepository.existsByEstudianteIdAndMateriaId(id_estudiante, id_materia))
      throw new RuntimeException("El estudiante ya está matriculado en la materia");

    Estudiante estudiante = estudianteRepository.findById(id_estudiante)
        .orElseThrow(() -> new RuntimeException("Estudiante con id " + id_estudiante + " no encontrado"));

    Materia materia = materiaRepository.findById(id_materia)
        .orElseThrow(() -> new RuntimeException("Materia con id " + id_materia + " no encontrada"));

    matricula.setEstudiante(estudiante);
    matricula.setMateria(materia);

    return matriculaRepository.save(matricula);
  }

  @Transactional
  public Matricula actualizarMatricula(Long id, Matricula matriculaActualizada) {
    Matricula matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matricula no encontrada"));

    String codigo = matriculaActualizada.getCodigo();
    String descripcion = matriculaActualizada.getDescripcion();
    Long id_estudiante = matriculaActualizada.getId_estudiante();
    Long id_materia = matriculaActualizada.getId_materia();

    if (id_estudiante != null || id_materia != null) {
      if (id_estudiante != matricula.getId_estudiante() && id_materia != matricula.getId_materia()
          && matriculaRepository.existsByEstudianteIdAndMateriaId(id_estudiante, id_materia))
        throw new RuntimeException("El estudiante ya está matriculado en la materia");

      if (id_estudiante != null) {
        Estudiante estudiante = estudianteRepository.findById(id_estudiante)
            .orElseThrow(() -> new RuntimeException("Estudiante con id " + id_estudiante + " no encontrado"));
        matricula.setId_estudiante(id_estudiante);
        matricula.setEstudiante(estudiante);
      }

      if (id_materia != null) {
        Materia materia = materiaRepository.findById(id_materia)
            .orElseThrow(() -> new RuntimeException("Materia con id " + id_materia + " no encontrada"));
        matricula.setId_materia(id_materia);
        matricula.setMateria(materia);
      }
    }

    if (!codigo.isBlank()) {
      if (!codigo.equals(matricula.getCodigo()) && matriculaRepository.existsByCodigo(codigo))
        throw new RuntimeException("Ya existe una matricula con el código " + codigo);

      matricula.setCodigo(codigo);
    }
    if (!descripcion.isBlank()) {
      if (descripcion.length() < 10)
        throw new RuntimeException("La descripción de la matricula debe tener al menos 10 caracteres");

      matricula.setDescripcion(descripcion);
    }

    return matriculaRepository.save(matricula);
  }

  @Transactional
  public void eliminarMatricula(Long id) {
    matriculaRepository.deleteById(id);
  }
}
