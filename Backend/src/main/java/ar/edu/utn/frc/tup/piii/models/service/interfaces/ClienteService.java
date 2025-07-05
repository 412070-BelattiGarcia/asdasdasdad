package ar.edu.utn.frc.tup.piii.models.service.interfaces;

import ar.edu.utn.frc.tup.piii.dto.ClienteDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Cliente;

import java.util.List;

public interface ClienteService {

    List<ClienteDTO> findAll();
    ClienteDTO findById(Long id);

    Cliente findByEntity(Long id);
}
