package com.example.matriculas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUsuarioDTO {
  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}
