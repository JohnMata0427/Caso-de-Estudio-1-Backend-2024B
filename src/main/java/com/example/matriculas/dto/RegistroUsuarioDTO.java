package com.example.matriculas.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroUsuarioDTO {
  @NotBlank(message = "El nombre es obligatorio")
  @Length(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  @Length(min = 3, max = 20, message = "El apellido de usuario debe tener entre 3 y 20 caracteres")
  private String apellido;

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un caracter especial")
  private String password;
}
