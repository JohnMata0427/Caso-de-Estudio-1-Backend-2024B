package com.example.matriculas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.matriculas.dto.MateriaDTO;
import com.example.matriculas.models.Materia;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.repositories.MateriaRepository;
import com.example.matriculas.repositories.MatriculaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaService {

  @Autowired
  private MateriaRepository materiaRepository;

  @Autowired
  private MatriculaRepository matriculaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Materia> obtenerTodasLasMaterias() {
    return materiaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerMateriaPorId(Long id) {
    Materia materia = materiaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Materia con id " + id + " no encontrada"));

    List<Matricula> matriculas = matriculaRepository.findByMateriaId(id);

    return Optional.of(Map.of("materia", materia, "matriculas", matriculas));
  }

  @Transactional
  public Materia crearMateria(MateriaDTO materia) {
    String codigo = materia.getCodigo();

    if (materiaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una materia con el código " + codigo);

    Materia materiaToSave = modelMapper.map(materia, Materia.class);

    return materiaRepository.save(materiaToSave);
  }

  @Transactional
  public Materia actualizarMateria(Long id, MateriaDTO materiaActualizada) {
    Materia materia = materiaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Materia con id " + id + " no encontrada"));

    String codigo = materiaActualizada.getCodigo();

    if (!codigo.equals(materia.getCodigo()) && materiaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una materia con el código " + codigo);

    Materia materiaToUpdate = modelMapper.map(materiaActualizada, Materia.class);

    return materiaRepository.save(materiaToUpdate);
  }

  @Transactional
  public void eliminarMateria(Long id) {
    materiaRepository.deleteById(id);
  }
}