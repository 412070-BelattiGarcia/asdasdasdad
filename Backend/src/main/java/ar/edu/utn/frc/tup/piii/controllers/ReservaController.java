package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.ReservaDTO;
import ar.edu.utn.frc.tup.piii.dto.ReservaRequestDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Obtener todas las reservas", description = "Retorna una lista de todas las reservas con filtros opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas(
            @RequestParam(required = false) Long cliente_id,
            @RequestParam(required = false) Long videojuego_id,
            @RequestParam(required = false) Long puesto_id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_hora) {

        List<ReservaDTO> reservas;

        if (cliente_id != null || videojuego_id != null || puesto_id != null || fecha_hora != null) {
            reservas = reservaService.findByFilter(cliente_id, videojuego_id, puesto_id, fecha_hora);
        } else {
            reservas = reservaService.findAll();
        }

        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Obtener reserva por ID", description = "Retorna una reserva específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        ReservaDTO reserva = reservaService.findById(id);
        return ResponseEntity.ok(reserva);
    }

    @Operation(summary = "Crear nueva reserva", description = "Crea una nueva reserva con validaciones de negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto con reglas de negocio",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaRequestDTO reservaRequestDTO) {
        ReservaDTO reservaCreada = reservaService.create(reservaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaCreada);
    }
}