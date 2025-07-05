package ar.edu.utn.frc.tup.piii.models.service.interfaces;

import ar.edu.utn.frc.tup.piii.dto.PuestoJuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.PuestoJuego;

import java.util.List;

public interface PuestoJuegoService {

    List<PuestoJuegoDTO> findAll();
    PuestoJuegoDTO findById(Long id);
    PuestoJuego findByEntity(Long id);
}
