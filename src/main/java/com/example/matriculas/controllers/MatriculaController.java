package com.example.matriculas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.matriculas.dto.MatriculaDTO;
import com.example.matriculas.models.Matricula;
import com.example.matriculas.services.MatriculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matriculas", description = "Endpoints para gestionar las matriculas del sistema")
@SecurityRequirement(name = "Bearer Authentication")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class MatriculaController {

  @Autowired
  private MatriculaService matriculaService;

  @Operation(summary = "Obtener todas las matriculas", description = "Obtiene una lista con todas las matriculas registradas en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de matriculas obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula[].class))
  })
  @GetMapping
  public List<Matricula> getAllMatriculas() {
    return matriculaService.obtenerTodasLasMatriculas();
  }

  @Operation(summary = "Obtener matricula por ID", description = "Obtiene una matricula por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matricula obtenida exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de matricula no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getMatriculaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(matriculaService.obtenerMatriculaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear matrícula", description = "Crea una nueva matrícula en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula creada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una matrícula con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createMatricula(@Valid @RequestBody MatriculaDTO matricula) {
    try {
      return ResponseEntity.ok(matriculaService.crearMatricula(matricula));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar matrícula", description = "Actualiza una matrícula existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula actualizada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Matricula.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una matrícula con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMatricula(@PathVariable Long id,
      @Valid @RequestBody MatriculaDTO matriculaActualizada) {
    try {
      return ResponseEntity.ok(matriculaService.actualizarMatricula(id, matriculaActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar matrícula", description = "Elimina una matrícula existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula eliminada exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "No existe una matrícula con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMatricula(@PathVariable Long id) {
    try {
      matriculaService.eliminarMatricula(id);
      return ResponseEntity.ok(Map.of("response", "Matricula con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

}
