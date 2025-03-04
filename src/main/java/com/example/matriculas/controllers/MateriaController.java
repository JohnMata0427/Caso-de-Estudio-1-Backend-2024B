package com.example.matriculas.controllers;

import com.example.matriculas.dto.MateriaDTO;
import com.example.matriculas.models.Materia;
import com.example.matriculas.services.MateriaService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/materias")
public class MateriaController {

  @Autowired
  private MateriaService materiaService;

  @GetMapping
  public List<Materia> getAllMaterias() {
    return materiaService.obtenerTodasLasMaterias();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMateriaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(materiaService.obtenerMateriaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PostMapping
  public ResponseEntity<Object> createMateria(@Valid @RequestBody MateriaDTO materia) {
    try {
      return ResponseEntity.ok(materiaService.crearMateria(materia));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMateria(@PathVariable Long id, @Valid @RequestBody MateriaDTO materiaActualizada) {
    try {
      return ResponseEntity.ok(materiaService.actualizarMateria(id, materiaActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMateria(@PathVariable Long id) {
    try {
      materiaService.eliminarMateria(id);
      return ResponseEntity.ok(Map.of("response", "Materia con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}