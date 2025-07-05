package ar.edu.utn.frc.tup.piii.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puestoJuego")
public class PuestoJuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puestoJuego_id")
    private Long id;

    private String nombre;

    private String tipo;
}
