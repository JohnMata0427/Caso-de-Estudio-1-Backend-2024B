package com.example.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
  @NotBlank(message = "El nombre es obligatorio")
  @Length(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  @Length(min = 3, max = 40, message = "El apellido debe tener entre 3 y 40 caracteres")
  private String apellido;

  @Pattern(regexp = "^\\d{10}$", message = "La cédula debe tener exactamente 10 dígitos")
  private String cedula;

  @NotBlank(message = "La fecha de nacimiento es obligatoria")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Formato de fecha inválido (YYYY-MM-DD)")
  @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
  private LocalDate fecha_nacimiento;

  @NotBlank(message = "La ciudad es obligatoria")
  @Length(min = 3, max = 50, message = "La ciudad debe tener entre 3 y 50 caracteres")
  private String ciudad;

  @NotBlank(message = "La dirección es obligatoria")
  @Length(min = 10, max = 100, message = "La dirección debe tener entre 10 y 100 caracteres")
  private String direccion;

  @Pattern(regexp = "^09\\d{8}$", message = "El teléfono debe tener exactamente 10 dígitos y empezar con 09")
  private String telefono;

  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  public boolean isBetween18And40() {
    Integer edad = LocalDate.now().getYear() - fecha_nacimiento.getYear();
    return edad >= 18 && edad <= 40;
  }
}