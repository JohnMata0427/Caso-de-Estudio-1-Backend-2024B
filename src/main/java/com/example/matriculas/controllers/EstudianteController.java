package com.example.matriculas.controllers;

import com.example.matriculas.models.Estudiante;
import com.example.matriculas.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteController {

  @Autowired
  private EstudianteService estudianteService;

  @GetMapping
  public List<Estudiante> getAllEstudiantes() {
    return estudianteService.obtenerTodosLosEstudiantes();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
    return estudianteService.obtenerEstudiantePorId(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
    return estudianteService.crearEstudiante(estudiante);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id,
      @RequestBody Estudiante estudianteActualizado) {
    try {
      Estudiante estudiante = estudianteService.actualizarEstudiante(id, estudianteActualizado);
      return ResponseEntity.ok(estudiante);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
    estudianteService.eliminarEstudiante(id);
    return ResponseEntity.noContent().build();
  }
}