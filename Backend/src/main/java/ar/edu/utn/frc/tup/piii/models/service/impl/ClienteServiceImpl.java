package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.ClienteDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Cliente;
import ar.edu.utn.frc.tup.piii.models.repository.ClienteRepository;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ClienteService;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findById(Long id) {
        return null;
    }

    @Override
    public Cliente findByEntity(Long id) {
        return null;
    }
}
