package com.example.matriculas.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MateriaDTO", description = "Modelo de datos para el registro de materias que incluye información básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class MateriaDTO {
  @Hidden
  @Null(message = "El id de la materia no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Nombre de la materia", example = "Programación I", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 50)
  @NotBlank(message = "El nombre de la materia es obligatorio")
  @Length(min = 3, max = 50, message = "El nombre de la materia debe tener entre 3 y 50 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre de la materia solo puede contener letras, espacios y caracteres especiales")
  private String nombre;

  @Schema(description = "Código único de la materia", example = "TDSD214", pattern = "^[A-Z]{4}\\d{3}$", minLength = 7, maxLength = 7)
  @NotBlank(message = "El código de la materia es obligatorio")
  @Pattern(regexp = "^[A-Z]{4}\\d{3}$", message = "El código de la materia debe tener 4 letras mayúsculas seguidas de 3 dígitos")
  private String codigo;

  @Schema(description = "Número de créditos de la materia", example = "3", minimum = "1", maximum = "5")
  @NotNull(message = "El número de créditos es obligatorio")
  @Positive(message = "El número de créditos debe ser un número positivo")
  @Max(value = 5, message = "El número de créditos no puede ser mayor a 5")
  @Digits(integer = 1, fraction = 0, message = "El número de créditos debe ser un número entero de máximo 1 dígito")
  private Integer creditos;

  @Schema(description = "Descripción de la materia", example = "En esta materia se estudian los conceptos básicos de programación en C++", minLength = 10, maxLength = 200)
  @NotBlank(message = "La descripción de la materia es obligatoria")
  @Length(min = 10, max = 200, message = "La descripción de la materia debe tener entre 10 y 200 caracteres")
  @Pattern(regexp = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){4}).{10,200}$", message = "La descripción debe tener al menos 4 letras")
  private String descripcion;
}
