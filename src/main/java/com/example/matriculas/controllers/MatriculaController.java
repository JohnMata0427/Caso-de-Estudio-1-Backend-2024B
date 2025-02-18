package com.example.matriculas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.services.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;

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
  public ResponseEntity<Matricula> getMatriculaById(@PathVariable Long id) {
    return matriculaService.obtenerMatriculaPorId(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Matricula createMatricula(@RequestBody Matricula matricula) {
    return matriculaService.crearMatricula(matricula);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Matricula> updateMatricula(@PathVariable Long id, @RequestBody Matricula matriculaActualizada) {
    try {
      Matricula matricula = matriculaService.actualizarMatricula(id, matriculaActualizada);
      return ResponseEntity.ok(matricula);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMatricula(@PathVariable Long id) {
    matriculaService.eliminarMatricula(id);
    return ResponseEntity.noContent().build();
  }

}
