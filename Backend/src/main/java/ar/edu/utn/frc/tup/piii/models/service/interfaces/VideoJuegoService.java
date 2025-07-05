package ar.edu.utn.frc.tup.piii.models.service.interfaces;

import ar.edu.utn.frc.tup.piii.dto.VideojuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Videojuego;

import java.util.List;

public interface VideoJuegoService {

    List<VideojuegoDTO> findAll();
    VideojuegoDTO findById(Long id);
    Videojuego findByEntity(Long id);
}
