package com.example.matriculas.controllers;

import com.example.matriculas.dto.EstudianteDTO;
import com.example.matriculas.models.Estudiante;
import com.example.matriculas.services.EstudianteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/estudiantes")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Estudiantes", description = "Endpoints para gestionar los estudiantes del sistema")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class EstudianteController {

  @Autowired
  private EstudianteService estudianteService;

  @Operation(summary = "Obtener todos los estudiantes", description = "Obtiene una lista con todos los estudiantes registrados en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Estudiante[].class))
  })
  @GetMapping
  public List<Estudiante> getAllEstudiantes() {
    return estudianteService.obtenerTodosLosEstudiantes();
  }

  @Operation(summary = "Obtener estudiante por ID", description = "Obtiene un estudiante por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Estudiante obtenido exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de estudiante no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getEstudianteById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(estudianteService.obtenerEstudiantePorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear estudiante", description = "Crea un nuevo estudiante en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Estudiante creado exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Estudiante.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe un estudiante con el mismo correo", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createEstudiante(@Valid @RequestBody EstudianteDTO estudiante) {
    try {
      return ResponseEntity.ok(estudianteService.crearEstudiante(estudiante));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar estudiante", description = "Actualiza un estudiante en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Estudiante.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o no existe un estudiante con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateEstudiante(@PathVariable Long id,
      @Valid @RequestBody EstudianteDTO estudianteActualizado) {
    try {
      return ResponseEntity.ok(estudianteService.actualizarEstudiante(id, estudianteActualizado));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante del sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Estudiante eliminado exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "No existe un estudiante con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteEstudiante(@PathVariable Long id) {
    try {
      estudianteService.eliminarEstudiante(id);
      return ResponseEntity.ok(Map.of("response", "Estudiante con id " + id + " eliminado exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}