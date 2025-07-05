package ar.edu.utn.frc.tup.piii.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;       // obligatorio

    @ManyToOne
    @JoinColumn(name = "videojuego_id", nullable = false)
    private Videojuego videojuego; // obligatorio

    @ManyToOne
    @JoinColumn(name = "puestoJuego_id", nullable = false)
    private PuestoJuego puesto;    // obligatorio

    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora; // obligatorio

    @Column(name = "duracionMinutos", nullable = false) // Corregido: era duracion
    private Integer duracionMinutos; // obligatorio

    @Column(name = "observaciones")
    private String observaciones;
}