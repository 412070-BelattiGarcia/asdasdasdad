package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.PuestoJuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.PuestoJuego;
import ar.edu.utn.frc.tup.piii.models.repository.PuestoJuegoRepository;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.PuestoJuegoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuestoJuegoServiceImpl implements PuestoJuegoService {

    private final PuestoJuegoRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public PuestoJuegoServiceImpl(PuestoJuegoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.mapper = modelMapper;
    }



    @Override
    public List<PuestoJuegoDTO> findAll() {
        return repository.findAll().stream()
                .map(puesto -> mapper.map(puesto, PuestoJuegoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PuestoJuegoDTO findById(Long id) {
        PuestoJuego puesto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puesto de juego no encontrado con ID: " + id));
        return mapper.map(puesto, PuestoJuegoDTO.class);
    }

    @Override
    public PuestoJuego findByEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puesto de juego no encontrado con ID: " + id));
    }
}