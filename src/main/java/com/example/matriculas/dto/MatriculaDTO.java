package com.example.matriculas.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MatriculaDTO", description = "Modelo de datos para el registro de materias que incluye información básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class MatriculaDTO {
  @Hidden
  @Null(message = "El id de la matrícula no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Código único de la matrícula", example = "2024B0001", pattern = "^(20\\d{2})[AB](\\d{4})$", minLength = 7, maxLength = 7)
  @NotBlank(message = "El código de la matrícula es obligatorio")
  @Pattern(regexp = "^(20\\d{2})[AB](\\d{4})$", message = "El código debe tener el formato de año, periodo (A o B) y numeración de 4 digitos (Ejemplo: 2024B0001)")
  private String codigo;

  @Schema(description = "Descripción de la matrícula", example = "Segunda matrícula del estudiante Juan Pérez en la materia de Programación I", minLength = 10, maxLength = 200)
  @NotBlank(message = "La descripción de la matrícula es obligatoria")
  @Length(min = 10, max = 200, message = "La descripción de la matrícula debe tener entre 10 y 200 caracteres")
  @Pattern(regexp = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){4}).{10,200}$", message = "La descripción debe tener al menos 4 letras")
  private String descripcion;

  @Schema(description = "ID del estudiante", example = "1", minimum = "1", maximum = "999")
  @NotNull(message = "El id del estudiante es obligatorio")
  @Positive(message = "El id del estudiante debe ser un número positivo")
  @Digits(integer = 3, fraction = 0, message = "El id del estudiante debe ser un número entero de máximo 3 dígitos")
  private Long id_estudiante;

  @Schema(description = "ID de la materia", example = "1", minimum = "1", maximum = "999")
  @NotNull(message = "El id de la materia es obligatorio")
  @Positive(message = "El id de la materia debe ser un número positivo")
  @Digits(integer = 3, fraction = 0, message = "El id de la materia debe ser un número entero de máximo 3 dígitos")
  private Long id_materia;

  @Hidden
  public boolean isValidYearInCode() {
    if (codigo == null || codigo.length() < 4)
      return false;

    int yearInCode = Integer.parseInt(codigo.substring(0, 4));

    int currentYear = LocalDate.now().getYear();

    return yearInCode <= currentYear;
  }
}
