package com.example.matriculas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.matriculas.models.Materia;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.repositories.MateriaRepository;
import com.example.matriculas.repositories.MatriculaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaService {

  @Autowired
  private MateriaRepository materiaRepository;

  @Autowired
  private MatriculaRepository matriculaRepository;

  @Transactional(readOnly = true)
  public List<Materia> obtenerTodasLasMaterias() {
    return materiaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerMateriaPorId(Long id) {
    Materia materia = materiaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

    List<Matricula> matriculas = matriculaRepository.findByMateriaId(id);

    return Optional.of(Map.of("materia", materia, "matriculas", matriculas));
  }

  @Transactional
  public Materia crearMateria(Materia materia) {
    String nombre = materia.getNombre();
    String codigo = materia.getCodigo();
    String descripcion = materia.getDescripcion();
    Integer creditos = materia.getCreditos();

    if (nombre.isBlank() || codigo.isBlank() || descripcion.isBlank() || creditos == null)
      throw new RuntimeException("Todos los campos son requeridos");

    if (codigo.length() < 6 || codigo.length() > 8)
      throw new RuntimeException("El código de la materia debe tener entre 6 y 8 caracteres");

    if (nombre.length() < 3)
      throw new RuntimeException("El nombre de la materia debe tener al menos 3 caracteres");

    if (descripcion.length() < 10)
      throw new RuntimeException("La descripción de la materia debe tener al menos 10 caracteres");

    if (materiaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una materia con el código " + codigo);

    if (creditos < 0)
      throw new RuntimeException("Los créditos no pueden ser negativos");

    return materiaRepository.save(materia);
  }

  @Transactional
  public Materia actualizarMateria(Long id, Materia materiaActualizada) {
    Materia materia = materiaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

    String nombre = materiaActualizada.getNombre();
    String codigo = materiaActualizada.getCodigo();
    String descripcion = materiaActualizada.getDescripcion();
    Integer creditos = materiaActualizada.getCreditos();

    if (!nombre.isBlank())
      materia.setNombre(nombre);

    if (!codigo.isBlank()) {
      if (!codigo.equals(materia.getCodigo()) && materiaRepository.existsByCodigo(codigo))
        throw new RuntimeException("Ya existe una materia con el código " + codigo);
      materia.setCodigo(codigo);
    }

    if (!descripcion.isBlank())
      materia.setDescripcion(descripcion);

    if (creditos != null) {
      if (creditos < 0)
        throw new RuntimeException("Los créditos no pueden ser negativos");
      materia.setCreditos(creditos);
    }

    return materiaRepository.save(materia);
  }

  @Transactional
  public void eliminarMateria(Long id) {
    materiaRepository.deleteById(id);
  }
}