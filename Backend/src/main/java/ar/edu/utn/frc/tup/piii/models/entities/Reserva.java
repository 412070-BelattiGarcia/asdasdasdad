package ar.edu.utn.frc.tup.piii.models.entities;

import java.time.LocalDateTime;

public class Reserva {
    Long id;
    Cliente cliente;       // obligatorio
    Videojuego videojuego; // obligatorio
    PuestoJuego puesto;    // obligatorio
    LocalDateTime fechaHora; // obligatorio
    Integer duracionMinutos; // obligatorio
    String observaciones;
}
