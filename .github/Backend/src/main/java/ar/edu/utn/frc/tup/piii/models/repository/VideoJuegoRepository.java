package ar.edu.utn.frc.tup.piii.models.repository;

import ar.edu.utn.frc.tup.piii.models.entities.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJuegoRepository extends JpaRepository<Videojuego, Long> {

}
