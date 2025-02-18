package com.example.matriculas.services;

import java.util.List;
import java.util.Optional;
import com.example.matriculas.models.Materia;
import com.example.matriculas.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaService {

  @Autowired
  private MateriaRepository materiaRepository;

  @Transactional(readOnly = true)
  public List<Materia> obtenerTodasLasMaterias() {
    return materiaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Materia> obtenerMateriaPorId(Long id) {
    return materiaRepository.findById(id);
  }

  @Transactional
  public Materia crearMateria(Materia materia) {
    return materiaRepository.save(materia);
  }

  @Transactional
  public Materia actualizarMateria(Long id, Materia materiaActualizada) {
    Materia materia = materiaRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    materia.setNombre(materiaActualizada.getNombre());
    materia.setCodigo(materiaActualizada.getCodigo());
    materia.setDescripcion(materiaActualizada.getDescripcion());
    materia.setCreditos(materiaActualizada.getCreditos());
    return materiaRepository.save(materia);
  }

  @Transactional
  public void eliminarMateria(Long id) {
    materiaRepository.deleteById(id);
  }
}