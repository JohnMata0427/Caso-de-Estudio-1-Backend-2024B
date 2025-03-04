package com.example.matriculas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.matriculas.dto.MatriculaDTO;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.services.MatriculaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

  @Autowired
  private MatriculaService matriculaService;

  @GetMapping
  public List<Matricula> getAllMatriculas() {
    return matriculaService.obtenerTodasLasMatriculas();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMatriculaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(matriculaService.obtenerMatriculaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PostMapping
  public ResponseEntity<Object> createMatricula(@Valid @RequestBody MatriculaDTO matricula) {
    try {
      return ResponseEntity.ok(matriculaService.crearMatricula(matricula));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMatricula(@PathVariable Long id, @Valid @RequestBody MatriculaDTO matriculaActualizada) {
    try {
      return ResponseEntity.ok(matriculaService.actualizarMatricula(id, matriculaActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMatricula(@PathVariable Long id) {
    try {
      matriculaService.eliminarMatricula(id);
      return ResponseEntity.ok(Map.of("response", "Matricula con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

}
