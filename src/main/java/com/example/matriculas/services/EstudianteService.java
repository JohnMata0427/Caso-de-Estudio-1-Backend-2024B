package com.example.matriculas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.matriculas.dto.EstudianteDTO;
import com.example.matriculas.models.Estudiante;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.repositories.EstudianteRepository;
import com.example.matriculas.repositories.MatriculaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteService {

  @Autowired
  private EstudianteRepository estudianteRepository;

  @Autowired
  private MatriculaRepository matriculaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Estudiante> obtenerTodosLosEstudiantes() {
    return estudianteRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerEstudiantePorId(Long id) {
    Estudiante estudiante = estudianteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudiante con id " + id + " no encontrado"));
    List<Matricula> matriculas = matriculaRepository.findByEstudianteId(id);

    return Optional.of(Map.of("estudiante", estudiante, "matriculas", matriculas));
  }

  @Transactional
  public Estudiante crearEstudiante(EstudianteDTO estudiante) {
    String cedula = estudiante.getCedula();
    String email = estudiante.getEmail();
    String telefono = estudiante.getTelefono();

    if (!estudiante.isBetween18And40())
      throw new RuntimeException("El estudiante debe tener entre 18 y 40 años");

    if (estudianteRepository.existsByCedula(cedula))
      throw new RuntimeException("Ya existe un estudiante con la cédula " + cedula);

    if (estudianteRepository.existsByEmail(email))
      throw new RuntimeException("Ya existe un estudiante con el email " + email);

    if (estudianteRepository.existsByTelefono(telefono))
      throw new RuntimeException("Ya existe un estudiante con el teléfono " + telefono);

    Estudiante estudianteToSave = modelMapper.map(estudiante, Estudiante.class);

    return estudianteRepository.save(estudianteToSave);
  }

  @Transactional
  public Estudiante actualizarEstudiante(Long id, EstudianteDTO estudianteActualizado) {
    Estudiante estudiante = estudianteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

    String cedula = estudianteActualizado.getCedula();
    LocalDate fecha_nacimiento = estudianteActualizado.getFecha_nacimiento();
    String telefono = estudianteActualizado.getTelefono();
    String email = estudianteActualizado.getEmail();

    if (!fecha_nacimiento.equals(estudiante.getFecha_nacimiento()) && !estudianteActualizado.isBetween18And40()) {
      throw new RuntimeException("El estudiante debe tener entre 18 y 40 años");
    }

    if (!cedula.equals(estudiante.getCedula()) && estudianteRepository.existsByCedula(cedula)) {
      throw new RuntimeException("Ya existe un estudiante con la cédula " + cedula);
    }

    if (!telefono.equals(estudiante.getTelefono()) && estudianteRepository.existsByTelefono(telefono)) {
      throw new RuntimeException("Ya existe un estudiante con el teléfono " + telefono);
    }

    if (!email.equals(estudiante.getEmail()) && estudianteRepository.existsByEmail(email)) {
      throw new RuntimeException("Ya existe un estudiante con el email " + email);
    }

    Estudiante estudianteToUpdate = modelMapper.map(estudianteActualizado, Estudiante.class);

    return estudianteRepository.save(estudianteToUpdate);
  }

  @Transactional
  public void eliminarEstudiante(Long id) {
    estudianteRepository.deleteById(id);
  }
}