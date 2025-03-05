package com.example.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "EstudianteDTO", description = "Modelo de datos para el registro de estudiantes que incluye información personal básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class EstudianteDTO {
  @Hidden
  @Null(message = "El id del estudiante no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Nombre del estudiante", example = "Juan", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 40)
  @NotBlank(message = "El nombre es obligatorio")
  @Length(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre solo puede contener letras y espacios")
  private String nombre;

  @Schema(description = "Apellido del estudiante", example = "Pérez", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 40)
  @NotBlank(message = "El apellido es obligatorio")
  @Length(min = 3, max = 40, message = "El apellido debe tener entre 3 y 40 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "El apellido solo puede contener letras y espacios")
  private String apellido;

  @Schema(description = "Cédula del estudiante", example = "1725034210", pattern = "^\\d{10}$", minLength = 10, maxLength = 10)
  @NotBlank(message = "La cédula es obligatoria")
  @Pattern(regexp = "^\\d{10}$", message = "La cédula debe tener exactamente 10 dígitos")
  private String cedula;

  @Schema(description = "Fecha de nacimiento del estudiante", example = "2000-01-01", format = "date")
  @NotNull(message = "La fecha de nacimiento es obligatoria")
  @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
  private LocalDate fecha_nacimiento;

  @Schema(description = "Ciudad de residencia del estudiante", example = "Quito", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 50)
  @NotBlank(message = "La ciudad es obligatoria")
  @Length(min = 3, max = 50, message = "La ciudad debe tener entre 3 y 50 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "La ciudad solo puede contener letras y espacios")
  private String ciudad;

  @Schema(description = "Dirección de residencia del estudiante", example = "Av. Amazonas N26-19 y Veintimilla", pattern = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){4}).{10,200}$", minLength = 10, maxLength = 200)
  @NotBlank(message = "La dirección es obligatoria")
  @Length(min = 10, max = 200, message = "La dirección debe tener entre 10 y 200 caracteres")
  @Pattern(regexp = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){4}).{10,200}$", message = "La dirección debe tener al menos 4 letras")
  private String direccion;

  @Schema(description = "Número de teléfono del estudiante", example = "0912345678", required = false, pattern = "^09\\d{8}$", minLength = 10, maxLength = 10)
  @NotBlank(message = "El teléfono es obligatorio")
  @Pattern(regexp = "^09\\d{8}$", message = "El teléfono debe tener exactamente 10 dígitos y empezar con 09")
  private String telefono;

  @Schema(description = "Correo electrónico del estudiante", example = "juanperez123@example.com", format = "email")
  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  @Hidden
  public boolean isBetween18And40() {
    Integer edad = LocalDate.now().getYear() - fecha_nacimiento.getYear();
    return edad >= 18 && edad <= 40;
  }
}