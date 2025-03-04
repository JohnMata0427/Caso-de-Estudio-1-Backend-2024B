package com.example.matriculas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.matriculas.repositories.MatriculaRepository;
import com.example.matriculas.dto.EstudianteDTO;
import com.example.matriculas.dto.MateriaDTO;
import com.example.matriculas.dto.MatriculaDTO;
import com.example.matriculas.models.Estudiante;
import com.example.matriculas.models.Materia;
import com.example.matriculas.models.Matricula;
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

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Matricula> obtenerTodasLasMatriculas() {
    return matriculaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerMatriculaPorId(Long id) {
    Matricula matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matricula con id " + id + " no encontrada"));

    Long id_estudiante = matricula.getId_estudiante();
    Estudiante estudiante = estudianteRepository.findById(id_estudiante)
        .orElseThrow(
            () -> new RuntimeException("Estudiante con id " + id_estudiante + " no encontrado"));

    Long id_materia = matricula.getId_materia();
    Materia materia = materiaRepository.findById(id_materia)
        .orElseThrow(() -> new RuntimeException("Materia con id " + id_materia + " no encontrada"));

    return Optional.of(Map.of("matricula", matricula, "estudiante", estudiante, "materia", materia));
  }

  @Transactional
  public Matricula crearMatricula(MatriculaDTO matricula) {

    String codigo = matricula.getCodigo();
    Long id_estudiante = matricula.getId_estudiante();
    Long id_materia = matricula.getId_materia();

    if (matriculaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una matricula con el código " + codigo);

    if (!matricula.isValidYearInCode())
      throw new RuntimeException("El año en el código de la matrícula debe estar entre el año 2000 y el año actual");

    if (matriculaRepository.existsByEstudianteIdAndMateriaId(id_estudiante, id_materia))
      throw new RuntimeException("El estudiante con id " + id_estudiante + " ya está matriculado en la materia con id "
          + id_materia);

    Estudiante estudiante = estudianteRepository.findById(id_estudiante)
        .orElseThrow(() -> new RuntimeException("Estudiante con id " + id_estudiante + " no encontrado"));

    Materia materia = materiaRepository.findById(id_materia)
        .orElseThrow(() -> new RuntimeException("Materia con id " + id_materia + " no encontrada"));

    EstudianteDTO estudianteToSave = modelMapper.map(estudiante, EstudianteDTO.class);
    MateriaDTO materiaToSave = modelMapper.map(materia, MateriaDTO.class);

    matricula.setEstudiante(estudianteToSave);
    matricula.setMateria(materiaToSave);

    Matricula matriculaToSave = modelMapper.map(matricula, Matricula.class);

    return matriculaRepository.save(matriculaToSave);
  }

  @Transactional
  public Matricula actualizarMatricula(Long id, MatriculaDTO matriculaActualizada) {
    Matricula matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("MatriculaDTO no encontrada"));

    String codigo = matriculaActualizada.getCodigo();
    Long id_estudiante = matriculaActualizada.getId_estudiante();
    Long id_materia = matriculaActualizada.getId_materia();

    if (!codigo.equals(matricula.getCodigo()) && matriculaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una matricula con el código " + codigo);

    if (!matriculaActualizada.isValidYearInCode())
      throw new RuntimeException("El año en el código de la matrícula debe estar entre el año 2000 y el año actual");

    Boolean actualizarEstudiante = !id_estudiante.equals(matricula.getId_estudiante());
    Boolean actualizarMateria = !id_materia.equals(matricula.getId_materia());

    if (actualizarEstudiante || actualizarMateria) {
      if (matriculaRepository.existsByEstudianteIdAndMateriaId(id_estudiante, id_materia))
        throw new RuntimeException(
            "El estudiante con id " + id_estudiante + " ya está matriculado en la materia con id " + id_materia);
    }

    if (actualizarEstudiante) {
      Estudiante estudiante = estudianteRepository.findById(id_estudiante)
          .orElseThrow(() -> new RuntimeException("Estudiante con id " + id_estudiante + " no encontrado"));

      EstudianteDTO estudianteToSave = modelMapper.map(estudiante, EstudianteDTO.class);

      matriculaActualizada.setEstudiante(estudianteToSave);
    }

    if (actualizarMateria) {
      Materia materia = materiaRepository.findById(id_materia)
          .orElseThrow(() -> new RuntimeException("Materia con id " + id_materia + " no encontrada"));

      MateriaDTO materiaToSave = modelMapper.map(materia, MateriaDTO.class);

      matriculaActualizada.setMateria(materiaToSave);
    }

    Matricula matriculaToUpdate = modelMapper.map(matriculaActualizada, Matricula.class);

    return matriculaRepository.save(matriculaToUpdate);
  }

  @Transactional
  public void eliminarMatricula(Long id) {
    matriculaRepository.deleteById(id);
  }
}
