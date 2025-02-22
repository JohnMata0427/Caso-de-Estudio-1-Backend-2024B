package com.example.matriculas.controllers;

import com.example.matriculas.models.Usuario;
import com.example.matriculas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody Usuario usuario) {
		try {
			return ResponseEntity.ok(usuarioService.registro(usuario));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Usuario request) {
		try {
			return ResponseEntity.ok(usuarioService.iniciarSesion(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("response",
					e instanceof BadCredentialsException ? "Correo o contraseña incorrectos" : e.getMessage()));
		}
	}

	@GetMapping("/profile")
	public ResponseEntity<Object> profile() {
		try {
			return ResponseEntity.ok(usuarioService.perfil());
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
		}
	}
}