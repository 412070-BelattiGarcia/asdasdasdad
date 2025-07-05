package ar.edu.utn.frc.tup.piii.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuestoJuegoDTO {

    private Long id;
    private String nombre;
    private String tipo;
}