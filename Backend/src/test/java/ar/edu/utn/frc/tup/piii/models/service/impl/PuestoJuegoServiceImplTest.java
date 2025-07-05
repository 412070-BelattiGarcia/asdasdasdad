package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.PuestoJuegoDTO;
import ar.edu.utn.frc.tup.piii.models.entities.PuestoJuego;
import ar.edu.utn.frc.tup.piii.models.repository.PuestoJuegoRepository;
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
class PuestoJuegoServiceImplTest {

    @Mock
    private PuestoJuegoRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PuestoJuegoServiceImpl puestoJuegoService;

    private PuestoJuego puestoJuego;
    private PuestoJuegoDTO puestoJuegoDTO;

    @BeforeEach
    void setUp() {
        puestoJuego = new PuestoJuego();
        puestoJuego.setId(1L);
        puestoJuego.setNombre("Puesto Gaming 1");
        puestoJuego.setTipo("PC");

        puestoJuegoDTO = new PuestoJuegoDTO();
        puestoJuegoDTO.setId(1L);
        puestoJuegoDTO.setNombre("Puesto Gaming 1");
        puestoJuegoDTO.setTipo("PC");
    }

    @Test
    void findAll_ShouldReturnListOfPuestoJuegoDTOs() {
        // Given
        List<PuestoJuego> puestos = Arrays.asList(puestoJuego);
        when(repository.findAll()).thenReturn(puestos);
        when(mapper.map(puestoJuego, PuestoJuegoDTO.class)).thenReturn(puestoJuegoDTO);

        // When
        List<PuestoJuegoDTO> result = puestoJuegoService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(puestoJuegoDTO.getId(), result.get(0).getId());
        assertEquals(puestoJuegoDTO.getNombre(), result.get(0).getNombre());
        assertEquals(puestoJuegoDTO.getTipo(), result.get(0).getTipo());

        verify(repository).findAll();
        verify(mapper).map(puestoJuego, PuestoJuegoDTO.class);
    }

    @Test
    void findById_WhenPuestoExists_ShouldReturnPuestoJuegoDTO() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(puestoJuego));
        when(mapper.map(puestoJuego, PuestoJuegoDTO.class)).thenReturn(puestoJuegoDTO);

        // When
        PuestoJuegoDTO result = puestoJuegoService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(puestoJuegoDTO.getId(), result.getId());
        assertEquals(puestoJuegoDTO.getNombre(), result.getNombre());
        assertEquals(puestoJuegoDTO.getTipo(), result.getTipo());

        verify(repository).findById(1L);
        verify(mapper).map(puestoJuego, PuestoJuegoDTO.class);
    }

    @Test
    void findById_WhenPuestoNotExists_ShouldThrowException() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> puestoJuegoService.findById(1L));

        assertEquals("Puesto de juego no encontrado con ID: 1", exception.getMessage());
        verify(repository).findById(1L);
        verify(mapper, never()).map(any(), any());
    }

    @Test
    void findByEntity_WhenPuestoExists_ShouldReturnPuestoJuego() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(puestoJuego));

        // When
        PuestoJuego result = puestoJuegoService.findByEntity(1L);

        // Then
        assertNotNull(result);
        assertEquals(puestoJuego.getId(), result.getId());
        assertEquals(puestoJuego.getNombre(), result.getNombre());
        assertEquals(puestoJuego.getTipo(), result.getTipo());

        verify(repository).findById(1L);
    }

    @Test
    void findByEntity_WhenPuestoNotExists_ShouldThrowException() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> puestoJuegoService.findByEntity(1L));

        assertEquals("Puesto de juego no encontrado con ID: 1", exception.getMessage());
        verify(repository).findById(1L);
    }
}