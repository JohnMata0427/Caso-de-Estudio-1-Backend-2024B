package com.example.matriculas.models;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estudiantes")
@Hidden
public class Estudiante {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false, unique = true, length = 10)
  private String cedula;

  @Column(nullable = false)
  private LocalDate fecha_nacimiento;

  @Column(nullable = false)
  private String ciudad;

  @Column(nullable = false)
  private String direccion;

  @Column(nullable = false, unique = true, length = 10)
  private String telefono;

  @Column(nullable = false, unique = true)
  private String email;

  @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Matricula> matriculas;
}