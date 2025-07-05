package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.VideojuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Videojuego;
import ar.edu.utn.frc.tup.piii.models.repository.VideoJuegoRepository;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.VideoJuegoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoJuegoServiceImpl implements VideoJuegoService {

    private final VideoJuegoRepository videoJuegoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoJuegoServiceImpl(VideoJuegoRepository videoJuegoRepository,ModelMapper modelMapper) {
        this.videoJuegoRepository = videoJuegoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VideojuegoDTO> findAll() {
        return videoJuegoRepository.findAll().stream()
                .map(videojuego -> modelMapper.map(videojuego, VideojuegoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VideojuegoDTO findById(Long id) {
        Videojuego videojuego = videoJuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado con ID: " + id));
        return modelMapper.map(videojuego, VideojuegoDTO.class);
    }

    @Override
    public Videojuego findByEntity(Long id) {
        return videoJuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado con ID: " + id));
    }
}