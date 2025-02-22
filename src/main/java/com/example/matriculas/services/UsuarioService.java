package com.example.matriculas.services;

import com.example.matriculas.models.Usuario;
import com.example.matriculas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.matriculas.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Transactional
  public Map<String, Object> registro(Usuario usuario) {
    String nombre = usuario.getNombre();
    String apellido = usuario.getApellido();
    String email = usuario.getEmail();
    String password = usuario.getPassword();

    if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank())
      throw new RuntimeException("Todos los campos son requeridos");

    if (usuarioRepository.existsByEmail(email))
      throw new RuntimeException("Ya existe un usuario con el email " + email);

    Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");

    if (!pattern.matcher(password).matches())
      throw new RuntimeException(
          "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un caracter especial");

    usuario.setPassword(passwordEncoder.encode(password));
    usuarioRepository.save(usuario);

    return Map.of("response", "Usuario registrado exitosamente", "usuario", usuario);
  }

  @Transactional
  public Map<String, String> iniciarSesion(Usuario request) {
    String email = request.getEmail();
    String password = request.getPassword();

    if (email.isBlank() || password.isBlank())
      throw new RuntimeException("Todos los campos son requeridos");

    if (!usuarioRepository.existsByEmail(email))
      throw new RuntimeException("Usuario con email " + email + " no registrado");

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    String token = jwtService.generateToken(email);
    return Map.of("response", "El usuario ha iniciado sesión exitosamente", "token", token);
  }

  @Transactional(readOnly = true)
  public Map<String, Object> perfil() {
    @SuppressWarnings("null")
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    String email = (String) request.getAttribute("email");

    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuario con email " + email + " no encontrado"));

    return Map.of("response", "Perfil del usuario", "usuario", Map.of(
        "nombre", usuario.getNombre(),
        "apellido", usuario.getApellido(),
        "email", usuario.getEmail()));
  }
}