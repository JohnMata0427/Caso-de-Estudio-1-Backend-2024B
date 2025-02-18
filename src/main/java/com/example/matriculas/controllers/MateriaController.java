package com.example.matriculas.controllers;

import com.example.matriculas.models.Materia;
import com.example.matriculas.services.MateriaService;
import java.util.List;
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
  public ResponseEntity<Materia> getMateriaById(@PathVariable Long id) {
    return materiaService.obtenerMateriaPorId(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Materia createMateria(@RequestBody Materia materia) {
    return materiaService.crearMateria(materia);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Materia> updateMateria(@PathVariable Long id, @RequestBody Materia materiaActualizada) {
    try {
      Materia materia = materiaService.actualizarMateria(id, materiaActualizada);
      return ResponseEntity.ok(materia);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
    materiaService.eliminarMateria(id);
    return ResponseEntity.noContent().build();
  }
}