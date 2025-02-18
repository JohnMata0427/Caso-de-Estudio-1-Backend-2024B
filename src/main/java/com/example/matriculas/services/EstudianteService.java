package com.example.matriculas.services;

import java.util.List;
import java.util.Optional;
import com.example.matriculas.models.Estudiante;
import com.example.matriculas.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteService {

  @Autowired
  private EstudianteRepository estudianteRepository;

  @Transactional(readOnly = true)
  public List<Estudiante> obtenerTodosLosEstudiantes() {
    return estudianteRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Estudiante> obtenerEstudiantePorId(Long id) {
    return estudianteRepository.findById(id);
  }

  @Transactional
  public Estudiante crearEstudiante(Estudiante estudiante) {
    return estudianteRepository.save(estudiante);
  }

  @Transactional
  public Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado) {
    Estudiante estudiante = estudianteRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

    estudiante.setNombre(estudianteActualizado.getNombre());
    estudiante.setApellido(estudianteActualizado.getApellido());
    estudiante.setCedula(estudianteActualizado.getCedula());
    estudiante.setFecha_nacimiento(estudianteActualizado.getFecha_nacimiento());
    estudiante.setCiudad(estudianteActualizado.getCiudad());
    estudiante.setDireccion(estudianteActualizado.getDireccion());
    estudiante.setTelefono(estudianteActualizado.getTelefono());
    estudiante.setEmail(estudianteActualizado.getEmail());

    return estudianteRepository.save(estudiante);
  }

  @Transactional
  public void eliminarEstudiante(Long id) {
    estudianteRepository.deleteById(id);
  }
}