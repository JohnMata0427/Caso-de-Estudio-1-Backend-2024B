package com.example.matriculas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matriculas")
@Hidden
public class Matricula {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 9)
  private String codigo;

  @Column(nullable = false)
  private String descripcion;

  @Column(nullable = false)
  private Long id_estudiante;

  @Column(nullable = false)
  private Long id_materia;

  @ManyToOne
  @JoinColumn(name = "id_estudiante", insertable = false, updatable = false)
  @JsonIgnore
  private Estudiante estudiante;

  @ManyToOne
  @JoinColumn(name = "id_materia", insertable = false, updatable = false)
  @JsonIgnore
  private Materia materia;
}
