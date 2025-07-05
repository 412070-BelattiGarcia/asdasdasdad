package ar.edu.utn.frc.tup.piii.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservaRequestDTO {

    @JsonProperty("cliente_id")
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @JsonProperty("videojuego_id")
    @NotNull(message = "El videojuego es obligatorio")
    private Long videojuegoId;

    @JsonProperty("puesto_id")
    @NotNull(message = "El puesto es obligatorio")
    private Long puestoId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @NotNull(message = "La duración es obligatoria")
    private Integer duracionMinutos;

    private String observaciones;
}
