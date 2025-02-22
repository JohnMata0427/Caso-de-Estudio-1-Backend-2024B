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
import java.util.Map;

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
  public ResponseEntity<Object> getEstudianteById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(estudianteService.obtenerEstudiantePorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PostMapping
  public ResponseEntity<Object> createEstudiante(@RequestBody Estudiante estudiante) {
    try {
      return ResponseEntity.ok(estudianteService.crearEstudiante(estudiante));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateEstudiante(@PathVariable Long id,
      @RequestBody Estudiante estudianteActualizado) {
    try {
      return ResponseEntity.ok(estudianteService.actualizarEstudiante(id, estudianteActualizado));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteEstudiante(@PathVariable Long id) {
    try {
      estudianteService.eliminarEstudiante(id);
      return ResponseEntity.ok(Map.of("response", "Estudiante eliminado correctamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}