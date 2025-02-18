package com.example.matriculas.services;

import java.util.List;
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
  public Optional<Matricula> obtenerMatriculaPorId(Long id) {
    return matriculaRepository.findById(id);
  }

  @Transactional
  public Matricula crearMatricula(Matricula matricula) {
    Estudiante estudiante = estudianteRepository.findById(matricula.getId_estudiante())
        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

    Materia materia = materiaRepository.findById(matricula.getId_materia())
        .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

    matricula.setEstudiante(estudiante);
    matricula.setMateria(materia);

    return matriculaRepository.save(matricula);
  }

  @Transactional
  public Matricula actualizarMatricula(Long id, Matricula matriculaActualizada) {
    Matricula matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matricula no encontrada"));
    matricula.setEstudiante(matriculaActualizada.getEstudiante());
    matricula.setMateria(matriculaActualizada.getMateria());
    matricula.setCodigo(matriculaActualizada.getCodigo());
    matricula.setDescripcion(matriculaActualizada.getDescripcion());
    return matriculaRepository.save(matricula);
  }

  @Transactional
  public void eliminarMatricula(Long id) {
    matriculaRepository.deleteById(id);
  }
}
