package ar.edu.utn.frc.tup.piii.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideojuegoDTO {

    private Long id;
    private String titulo;
    private String genero;
}