package ar.edu.utn.frc.tup.piii.models.repository;

import ar.edu.utn.frc.tup.piii.models.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE " +
            "(:clienteId IS NULL OR r.cliente.id = :clienteId) AND " +
            "(:videojuegoId IS NULL OR r.videojuego.id = :videojuegoId) AND " +
            "(:puestoId IS NULL OR r.puesto.id = :puestoId) AND " +
            "(:fechaHora IS NULL OR r.fechaHora = :fechaHora)")
    List<Reserva> findByFilters(@Param("clienteId") Long clienteId,
                                @Param("videojuegoId") Long videojuegoId,
                                @Param("puestoId") Long puestoId,
                                @Param("fechaHora") LocalDateTime fechaHora);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE " +
            "r.cliente.id = :clienteId AND " +
            "DATE(r.fechaHora) = DATE(:fechaHora)")
    boolean existsReservaByClienteAndDate(@Param("clienteId") Long clienteId,
                                          @Param("fechaHora") LocalDateTime fechaHora);

    // Consulta corregida para validar solapamiento de reservas
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE " +
            "r.puesto.id = :puestoId AND " +
            "((r.fechaHora < :fechaHoraFin AND " +
            "DATEADD(MINUTE, r.duracionMinutos, r.fechaHora) > :fechaHora))")
    boolean existsOverlappingReserva(@Param("puestoId") Long puestoId,
                                     @Param("fechaHora") LocalDateTime fechaHora,
                                     @Param("fechaHoraFin") LocalDateTime fechaHoraFin);
}