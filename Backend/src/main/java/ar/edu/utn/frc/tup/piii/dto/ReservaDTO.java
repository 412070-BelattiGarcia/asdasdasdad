package ar.edu.utn.frc.tup.piii.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaDTO {

    private Long id;
    private ClienteDTO cliente;
    private VideojuegoDTO videojuego;
    private PuestoJuegoDTO puestoJuego; // Corregido: era puestojuego

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaHora;

    private Integer duracionMinutos;

    private String observaciones; // Corregido: era observacion
}