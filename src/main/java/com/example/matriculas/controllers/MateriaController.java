package com.example.matriculas.controllers;

import com.example.matriculas.dto.MateriaDTO;
import com.example.matriculas.models.Materia;
import com.example.matriculas.services.MateriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/materias")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Materias", description = "Endpoints para gestionar las materias del sistema")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class MateriaController {

  @Autowired
  private MateriaService materiaService;

  @Operation(summary = "Obtener todas las materias", description = "Obtiene una lista con todas las materias registradas en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de materias obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Materia[].class))
  })
  @GetMapping
  public List<Materia> getAllMaterias() {
    return materiaService.obtenerTodasLasMaterias();
  }

  @Operation(summary = "Obtener materia por ID", description = "Obtiene una materia por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Materia obtenida exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de materia no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getMateriaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(materiaService.obtenerMateriaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear materia", description = "Crea una nueva materia en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Materia creada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Materia.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una materia con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createMateria(@Valid @RequestBody MateriaDTO materia) {
    try {
      return ResponseEntity.ok(materiaService.crearMateria(materia));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar materia", description = "Actualiza una materia existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Materiaactualizada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Materia.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o no existe una materia con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMateria(@PathVariable Long id,
      @Valid @RequestBody MateriaDTO materiaActualizada) {
    try {
      return ResponseEntity.ok(materiaService.actualizarMateria(id, materiaActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar materia", description = "Elimina una materia del sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Materia eliminada exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de materia no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMateria(@PathVariable Long id) {
    try {
      materiaService.eliminarMateria(id);
      return ResponseEntity.ok(Map.of("response", "Materia con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}