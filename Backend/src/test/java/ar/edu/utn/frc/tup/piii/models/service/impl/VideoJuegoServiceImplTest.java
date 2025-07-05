package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.VideojuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Videojuego;
import ar.edu.utn.frc.tup.piii.models.repository.VideoJuegoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoJuegoServiceImplTest {

    @Mock
    private VideoJuegoRepository videoJuegoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VideoJuegoServiceImpl videoJuegoService;

    private Videojuego videojuego;
    private VideojuegoDTO videojuegoDTO;

    @BeforeEach
    void setUp() {
        videojuego = new Videojuego();
        videojuego.setId(1L);
        videojuego.setTitulo("FIFA 24");
        videojuego.setGenero("Deportes");

        videojuegoDTO = new VideojuegoDTO();
        videojuegoDTO.setId(1L);
        videojuegoDTO.setTitulo("FIFA 24");
        videojuegoDTO.setGenero("Deportes");
    }

    @Test
    void findAll_ShouldReturnListOfVideojuegoDTOs() {
        // Given
        List<Videojuego> videojuegos = Arrays.asList(videojuego);
        when(videoJuegoRepository.findAll()).thenReturn(videojuegos);
        when(modelMapper.map(videojuego, VideojuegoDTO.class)).thenReturn(videojuegoDTO);

        // When
        List<VideojuegoDTO> result = videoJuegoService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(videojuegoDTO.getId(), result.get(0).getId());
        assertEquals(videojuegoDTO.getTitulo(), result.get(0).getTitulo());
        assertEquals(videojuegoDTO.getGenero(), result.get(0).getGenero());

        verify(videoJuegoRepository).findAll();
        verify(modelMapper).map(videojuego, VideojuegoDTO.class);
    }

    @Test
    void findById_WhenVideojuegoExists_ShouldReturnVideojuegoDTO() {
        // Given
        when(videoJuegoRepository.findById(1L)).thenReturn(Optional.of(videojuego));
        when(modelMapper.map(videojuego, VideojuegoDTO.class)).thenReturn(videojuegoDTO);

        // When
        VideojuegoDTO result = videoJuegoService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(videojuegoDTO.getId(), result.getId());
        assertEquals(videojuegoDTO.getTitulo(), result.getTitulo());
        assertEquals(videojuegoDTO.getGenero(), result.getGenero());

        verify(videoJuegoRepository).findById(1L);
        verify(modelMapper).map(videojuego, VideojuegoDTO.class);
    }

    @Test
    void findById_WhenVideojuegoNotExists_ShouldThrowException() {
        // Given
        when(videoJuegoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> videoJuegoService.findById(1L));

        assertEquals("Videojuego no encontrado con ID: 1", exception.getMessage());
        verify(videoJuegoRepository).findById(1L);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void findByEntity_WhenVideojuegoExists_ShouldReturnVideojuego() {
        // Given
        when(videoJuegoRepository.findById(1L)).thenReturn(Optional.of(videojuego));

        // When
        Videojuego result = videoJuegoService.findByEntity(1L);

        // Then
        assertNotNull(result);
        assertEquals(videojuego.getId(), result.getId());
        assertEquals(videojuego.getTitulo(), result.getTitulo());
        assertEquals(videojuego.getGenero(), result.getGenero());

        verify(videoJuegoRepository).findById(1L);
    }

    @Test
    void findByEntity_WhenVideojuegoNotExists_ShouldThrowException() {
        // Given
        when(videoJuegoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> videoJuegoService.findByEntity(1L));

        assertEquals("Videojuego no encontrado con ID: 1", exception.getMessage());
        verify(videoJuegoRepository).findById(1L);
    }
}