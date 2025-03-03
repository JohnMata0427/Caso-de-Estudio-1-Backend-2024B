package com.example.matriculas.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {
  @NotBlank(message = "El código de la matrícula es obligatorio")
  @Pattern(regexp = "^\\20d{2}[AB]\\d{4}$", message = "El código de la matrícula debe tener el formato Año-Periodo-Numeración (Ejemplo: 2022A0001)")
  private String codigo;

  @NotBlank(message = "La descripción de la matrícula es obligatoria")
  @Length(min = 10, max = 200, message = "La descripción de la matrícula debe tener entre 10 y 200 caracteres")
  private String descripcion;

  @NotBlank(message = "El id del estudiante es obligatorio")
  @Positive(message = "El id del estudiante debe ser un número positivo")
  private Long id_estudiante;

  @NotBlank(message = "El id de la materia es obligatorio")
  @Positive(message = "El id de la materia debe ser un número positivo")
  private Long id_materia;

  private EstudianteDTO estudiante;
  private MateriaDTO materia;
}
