package com.example.matriculas.controllers;

import com.example.matriculas.models.Usuario;
import com.example.matriculas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Map<String, Object>> register(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.registro(usuario));
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Usuario request) {
		return ResponseEntity.ok(usuarioService.iniciarSesion(request));
	}

	@GetMapping("/profile")
	public ResponseEntity<Map<String, Object>> profile() {
		System.out.println("Profile");
		return ResponseEntity.ok(usuarioService.perfil());
	}
}