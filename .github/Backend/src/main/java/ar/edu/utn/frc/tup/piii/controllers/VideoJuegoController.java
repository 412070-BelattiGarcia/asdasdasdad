package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.VideojuegoDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.VideoJuegoService;
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
@RequestMapping("/api/v1/videojuegos")
public class VideoJuegoController {

    @Autowired
    private VideoJuegoService videoJuegoService;

    @Operation(summary = "Obtener todos los videojuegos", description = "Retorna una lista de todos los videojuegos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de videojuegos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping
    public ResponseEntity<List<VideojuegoDTO>> getAllVideojuegos() {
        List<VideojuegoDTO> videojuegos = videoJuegoService.findAll();
        return ResponseEntity.ok(videojuegos);
    }

    @Operation(summary = "Obtener videojuego por ID", description = "Retorna un videojuego específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego encontrado"),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoDTO> getVideojuegoById(@PathVariable Long id) {
        VideojuegoDTO videojuego = videoJuegoService.findById(id);
        return ResponseEntity.ok(videojuego);
    }
}