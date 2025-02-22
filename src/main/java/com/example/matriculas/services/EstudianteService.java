package com.example.matriculas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.matriculas.models.Estudiante;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.repositories.EstudianteRepository;
import com.example.matriculas.repositories.MatriculaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteService {

  @Autowired
  private EstudianteRepository estudianteRepository;

  @Autowired
  private MatriculaRepository matriculaRepository;

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
  public Estudiante crearEstudiante(Estudiante estudiante) {

    String nombre = estudiante.getNombre();
    String apellido = estudiante.getApellido();
    String cedula = estudiante.getCedula();
    LocalDate fecha_nacimiento = estudiante.getFecha_nacimiento();
    String ciudad = estudiante.getCiudad();
    String direccion = estudiante.getDireccion();
    String telefono = estudiante.getTelefono();
    String email = estudiante.getEmail();

    if (nombre.isBlank() || apellido.isBlank() || cedula.isBlank() || ciudad.isBlank() || direccion.isBlank()
        || telefono.isBlank() || email.isBlank() || fecha_nacimiento.toString().isBlank())
      throw new RuntimeException("Todos los campos son requeridos");

    Integer edad = fecha_nacimiento.until(LocalDate.now()).getYears();

    if (nombre.length() < 3)
      throw new RuntimeException("El nombre debe tener al menos 3 caracteres");

    if (apellido.length() < 3)
      throw new RuntimeException("El apellido debe tener al menos 3 caracteres");

    if (ciudad.length() < 3)
      throw new RuntimeException("La ciudad debe tener al menos 3 caracteres");

    if (direccion.length() < 8)
      throw new RuntimeException("La dirección debe tener al menos 8 caracteres");

    if (edad < 16 || edad > 40)
      throw new RuntimeException("El estudiante debe tener entre 16 y 40 años");

    if (cedula.length() != 10)
      throw new RuntimeException("La cédula debe tener 10 dígitos");

    if (telefono.length() != 10)
      throw new RuntimeException("El teléfono debe tener 10 dígitos");

    if (estudianteRepository.existsByCedula(cedula))
      throw new RuntimeException("Ya existe un estudiante con la cédula " + cedula);

    if (estudianteRepository.existsByEmail(email))
      throw new RuntimeException("Ya existe un estudiante con el email " + email);

    if (estudianteRepository.existsByTelefono(telefono))
      throw new RuntimeException("Ya existe un estudiante con el teléfono " + telefono);

    return estudianteRepository.save(estudiante);
  }

  @Transactional
  public Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado) {
    Estudiante estudiante = estudianteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

    String nombre = estudianteActualizado.getNombre();
    String apellido = estudianteActualizado.getApellido();
    String cedula = estudianteActualizado.getCedula();
    LocalDate fecha_nacimiento = estudianteActualizado.getFecha_nacimiento();
    String ciudad = estudianteActualizado.getCiudad();
    String direccion = estudianteActualizado.getDireccion();
    String telefono = estudianteActualizado.getTelefono();
    String email = estudianteActualizado.getEmail();

    if (!nombre.isBlank()) {
      if (nombre.length() < 3)
        throw new RuntimeException("El nombre debe tener al menos 3 caracteres");

      estudiante.setNombre(nombre);
    }

    if (!apellido.isBlank()) {
      if (apellido.length() < 3)
        throw new RuntimeException("El apellido debe tener al menos 3 caracteres");

      estudiante.setApellido(apellido);
    }

    if (!cedula.isBlank()) {
      if (cedula.length() != 10)
        throw new RuntimeException("La cédula debe tener 10 dígitos");

      if (!cedula.equals(estudiante.getCedula()) && estudianteRepository.existsByCedula(cedula))
        throw new RuntimeException("Ya existe un estudiante con la cédula " + cedula);

      estudiante.setCedula(cedula);
    }

    if (!fecha_nacimiento.toString().isBlank()) {
      Integer edad = fecha_nacimiento.until(LocalDate.now()).getYears();

      if (edad < 16 || edad > 40)
        throw new RuntimeException("El estudiante debe tener entre 16 y 40 años");

      estudiante.setFecha_nacimiento(fecha_nacimiento);
    }

    if (!ciudad.isBlank()) {
      if (ciudad.length() < 3)
        throw new RuntimeException("La ciudad debe tener al menos 3 caracteres");
      estudiante.setCiudad(ciudad);
    }

    if (!direccion.isBlank()) {
      if (direccion.length() < 8)
        throw new RuntimeException("La dirección debe tener al menos 8 caracteres");
      estudiante.setDireccion(direccion);
    }

    if (!telefono.isBlank()) {
      if (telefono.length() != 10)
        throw new RuntimeException("El teléfono debe tener 10 dígitos");
      if (!telefono.equals(estudiante.getTelefono()) && estudianteRepository.existsByTelefono(telefono))
        throw new RuntimeException("Ya existe un estudiante con el teléfono " + telefono);
      estudiante.setTelefono(telefono);
    }

    if (!email.isBlank()) {
      if (!email.equals(estudiante.getEmail()) && estudianteRepository.existsByEmail(email))
        throw new RuntimeException("Ya existe un estudiante con el email " + email);
      estudiante.setEmail(email);
    }

    return estudianteRepository.save(estudiante);
  }

  @Transactional
  public void eliminarEstudiante(Long id) {
    estudianteRepository.deleteById(id);
  }
}