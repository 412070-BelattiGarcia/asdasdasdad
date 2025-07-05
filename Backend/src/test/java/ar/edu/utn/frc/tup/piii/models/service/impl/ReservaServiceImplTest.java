package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.ReservaDTO;
import ar.edu.utn.frc.tup.piii.dto.ReservaRequestDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Cliente;
import ar.edu.utn.frc.tup.piii.models.entities.PuestoJuego;
import ar.edu.utn.frc.tup.piii.models.entities.Reserva;
import ar.edu.utn.frc.tup.piii.models.entities.Videojuego;
import ar.edu.utn.frc.tup.piii.models.repository.ReservaRepository;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ClienteService;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.PuestoJuegoService;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.VideoJuegoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PuestoJuegoService puestoJuegoService;

    @Mock
    private VideoJuegoService videoJuegoService;

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Reserva reserva;
    private ReservaDTO reservaDTO;
    private ReservaRequestDTO reservaRequestDTO;
    private Cliente cliente;
    private Videojuego videojuego;
    private PuestoJuego puestoJuego;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreCompleto("Juan Pérez");

        videojuego = new Videojuego();
        videojuego.setId(1L);
        videojuego.setTitulo("FIFA 24");

        puestoJuego = new PuestoJuego();
        puestoJuego.setId(1L);
        puestoJuego.setNombre("Puesto 1");

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setCliente(cliente);
        reserva.setVideojuego(videojuego);
        reserva.setPuesto(puestoJuego);
        reserva.setFechaHora(LocalDateTime.of(2025, 7, 15, 14, 0));
        reserva.setDuracionMinutos(60);

        reservaDTO = new ReservaDTO();
        reservaDTO.setId(1L);
        reservaDTO.setFechaHora(LocalDateTime.of(2025, 7, 15, 14, 0));
        reservaDTO.setDuracionMinutos(60);

        reservaRequestDTO = new ReservaRequestDTO();
        reservaRequestDTO.setClienteId(1L);
        reservaRequestDTO.setVideojuegoId(1L);
        reservaRequestDTO.setPuestoId(1L);
        reservaRequestDTO.setFechaHora(LocalDateTime.of(2025, 7, 15, 14, 0));
        reservaRequestDTO.setDuracionMinutos(60);
    }

    @Test
    void findAll_ShouldReturnListOfReservaDTOs() {
        // Given
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findAll()).thenReturn(reservas);
        when(modelMapper.map(reserva, ReservaDTO.class)).thenReturn(reservaDTO);

        // When
        List<ReservaDTO> result = reservaService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservaDTO.getId(), result.get(0).getId());

        verify(reservaRepository).findAll();
        verify(modelMapper).map(reserva, ReservaDTO.class);
    }

    @Test
    void findByFilter_ShouldReturnFilteredReservas() {
        // Given
        Long clienteId = 1L;
        Long videoJuegoId = 1L;
        Long puestoId = 1L;
        LocalDateTime fechaHora = LocalDateTime.of(2025, 7, 15, 14, 0);

        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findByFilters(clienteId, videoJuegoId, puestoId, fechaHora))
                .thenReturn(reservas);
        when(modelMapper.map(reserva, ReservaDTO.class)).thenReturn(reservaDTO);

        // When
        List<ReservaDTO> result = reservaService.findByFilter(clienteId, videoJuegoId, puestoId, fechaHora);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservaDTO.getId(), result.get(0).getId());

        verify(reservaRepository).findByFilters(clienteId, videoJuegoId, puestoId, fechaHora);
        verify(modelMapper).map(reserva, ReservaDTO.class);
    }

    @Test
    void findById_WhenReservaExists_ShouldReturnReservaDTO() {
        // Given
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(modelMapper.map(reserva, ReservaDTO.class)).thenReturn(reservaDTO);

        // When
        ReservaDTO result = reservaService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(reservaDTO.getId(), result.getId());

        verify(reservaRepository).findById(1L);
        verify(modelMapper).map(reserva, ReservaDTO.class);
    }

    @Test
    void findById_WhenReservaNotExists_ShouldThrowException() {
        // Given
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.findById(1L));

        assertEquals("Reserva no encontrada con ID: 1", exception.getMessage());
        verify(reservaRepository).findById(1L);
    }

    @Test
    void create_WhenValidRequest_ShouldCreateReserva() {
        // Given
        when(clienteService.findByEntity(1L)).thenReturn(cliente);
        when(videoJuegoService.findByEntity(1L)).thenReturn(videojuego);
        when(puestoJuegoService.findByEntity(1L)).thenReturn(puestoJuego);
        when(reservaRepository.existsReservaByClienteAndDate(any(), any())).thenReturn(false);
        when(reservaRepository.existsOverlappingReserva(any(), any(), any())).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        when(modelMapper.map(reserva, ReservaDTO.class)).thenReturn(reservaDTO);

        // When
        ReservaDTO result = reservaService.create(reservaRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(reservaDTO.getId(), result.getId());

        verify(clienteService).findByEntity(1L);
        verify(videoJuegoService).findByEntity(1L);
        verify(puestoJuegoService).findByEntity(1L);
        verify(reservaRepository).save(any(Reserva.class));
        verify(modelMapper).map(reserva, ReservaDTO.class);
    }

    @Test
    void create_WhenInvalidTimeRange_ShouldThrowException() {
        // Given - Reserva muy temprano (antes de las 10:00)
        reservaRequestDTO.setFechaHora(LocalDateTime.of(2025, 7, 15, 8, 0));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.create(reservaRequestDTO));

        assertEquals("La reserva debe estar dentro del horario permitido (10:00 - 22:00)",
                exception.getMessage());
    }

    @Test
    void create_WhenInvalidDuration_ShouldThrowException() {
        // Given - Duración no permitida
        reservaRequestDTO.setDuracionMinutos(45);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.create(reservaRequestDTO));

        assertEquals("La duración debe ser de 30, 60, 90 o 120 minutos",
                exception.getMessage());
    }

    @Test
    void create_WhenClienteHasReservaOnSameDate_ShouldThrowException() {
        // Given
        when(reservaRepository.existsReservaByClienteAndDate(any(), any())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.create(reservaRequestDTO));

        assertEquals("El cliente ya tiene una reserva para ese día",
                exception.getMessage());
    }

    @Test
    void create_WhenPuestoHasOverlappingReserva_ShouldThrowException() {
        // Given
        when(reservaRepository.existsReservaByClienteAndDate(any(), any())).thenReturn(false);
        when(reservaRepository.existsOverlappingReserva(any(), any(), any())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.create(reservaRequestDTO));

        assertEquals("El puesto ya está reservado en ese horario",
                exception.getMessage());
    }

    @Test
    void create_WhenEndTimeExceedsClosingTime_ShouldThrowException() {
        // Given - Reserva que termina después de las 22:00
        reservaRequestDTO.setFechaHora(LocalDateTime.of(2025, 7, 15, 21, 30));
        reservaRequestDTO.setDuracionMinutos(60); // Terminaría a las 22:30

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.create(reservaRequestDTO));

        assertEquals("La reserva debe estar dentro del horario permitido (10:00 - 22:00)",
                exception.getMessage());
    }
}