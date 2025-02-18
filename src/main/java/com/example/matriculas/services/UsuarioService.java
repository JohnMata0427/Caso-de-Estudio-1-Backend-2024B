package com.example.matriculas.services;

import com.example.matriculas.models.Usuario;
import com.example.matriculas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.matriculas.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

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
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    usuarioRepository.save(usuario);
    return Map.of("response", "Usuario registrado exitosamente", "usuario", usuario);
  }

  @Transactional
  public Map<String, String> iniciarSesion(Usuario request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    UserDetails userDetails = usuarioRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    String token = jwtService.generateToken(userDetails.getUsername());
    return Map.of("token", token, "response", "Login exitoso");
  }

  @Transactional(readOnly = true)
  public Map<String, Object> perfil() {
    @SuppressWarnings("null")
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String email = (String) request.getAttribute("email");

    Usuario response = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Map<String, Object> usuario = Map.of(
        "nombre", response.getNombre(),
        "apellido", response.getApellido(),
        "email", response.getEmail());

    return Map.of("response", "Perfil del usuario", "usuario", usuario);
  }
}