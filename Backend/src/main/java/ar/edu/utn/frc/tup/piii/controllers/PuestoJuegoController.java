package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.PuestoJuegoDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.PuestoJuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/puestos")
public class PuestoJuegoController {

    @Autowired
    private PuestoJuegoService puestoJuegoService;

    @Operation(summary = "Obtener todos los puestos de juego", description = "Retorna una lista de todos los puestos de juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de puestos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping
    public ResponseEntity<List<PuestoJuegoDTO>> getAllPuestos() {
        List<PuestoJuegoDTO> puestos = puestoJuegoService.findAll();
        return ResponseEntity.ok(puestos);
    }

    @Operation(summary = "Obtener puesto por ID", description = "Retorna un puesto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puesto encontrado"),
            @ApiResponse(responseCode = "404", description = "Puesto no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PuestoJuegoDTO> getPuestoById(@PathVariable Long id) {
        PuestoJuegoDTO puesto = puestoJuegoService.findById(id);
        return ResponseEntity.ok(puesto);
    }
}