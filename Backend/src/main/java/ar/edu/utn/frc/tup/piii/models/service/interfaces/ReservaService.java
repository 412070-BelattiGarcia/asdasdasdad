package ar.edu.utn.frc.tup.piii.models.service.interfaces;

import ar.edu.utn.frc.tup.piii.dto.ReservaDTO;
import ar.edu.utn.frc.tup.piii.dto.ReservaRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaService {

    List<ReservaDTO> findAll();
    List<ReservaDTO> findByFilter(Long clienteId, Long videoJuegoId, Long puestoJuegoId, LocalDateTime fechaHora);
    ReservaDTO findById(Long id);

    ReservaDTO create(ReservaRequestDTO reservaRequestDTO);
}
