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
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ReservaService;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.VideoJuegoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ModelMapper modelMapper;
    private final ClienteService clienteService;
    private final PuestoJuegoService puestoJuegoService;
    private final VideoJuegoService videoJuegoService;
    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaServiceImpl(
            ModelMapper modelMapper,
            ClienteService clienteService,
            PuestoJuegoService puestoJuegoService,
            VideoJuegoService videoJuegoService,
            ReservaRepository reservaRepository) {
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
        this.puestoJuegoService = puestoJuegoService;
        this.videoJuegoService = videoJuegoService;
        this.reservaRepository = reservaRepository;
    }

    private static final LocalTime HORA_APERTURA = LocalTime.of(10, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(22, 0);
    private static final List<Integer> DURACIONES_PERMITIDAS = Arrays.asList(30, 60, 90, 120);

    @Override
    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> findByFilter(Long clienteId, Long videoJuegoId, Long puestoJuegoId, LocalDateTime fechaHora) {
        List<Reserva> reservas = reservaRepository.findByFilters(clienteId, videoJuegoId, puestoJuegoId, fechaHora);
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        return modelMapper.map(reserva, ReservaDTO.class);
    }

    @Override
    public ReservaDTO create(ReservaRequestDTO reservaRequestDTO) {
        // Validar reglas de negocio
        validarReserva(reservaRequestDTO);

        // Obtener entidades relacionadas
        Cliente cliente = clienteService.findByEntity(reservaRequestDTO.getClienteId());
        Videojuego videojuego = videoJuegoService.findByEntity(reservaRequestDTO.getVideojuegoId());
        PuestoJuego puestoJuego = puestoJuegoService.findByEntity(reservaRequestDTO.getPuestoId());

        // Crear nueva reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setVideojuego(videojuego);
        reserva.setPuesto(puestoJuego);
        reserva.setFechaHora(reservaRequestDTO.getFechaHora());
        reserva.setDuracionMinutos(reservaRequestDTO.getDuracionMinutos());
        reserva.setObservaciones(reservaRequestDTO.getObservaciones());

        // Guardar reserva
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Retornar DTO
        return modelMapper.map(reservaGuardada, ReservaDTO.class);
    }

    private void validarReserva(ReservaRequestDTO reservaRequestDTO) {
        // Validar horario de funcionamiento
        LocalTime horaReserva = reservaRequestDTO.getFechaHora().toLocalTime();
        LocalTime horaFin = horaReserva.plusMinutes(reservaRequestDTO.getDuracionMinutos());

        if (horaReserva.isBefore(HORA_APERTURA) || horaFin.isAfter(HORA_CIERRE)) {
            throw new RuntimeException("La reserva debe estar dentro del horario permitido (10:00 - 22:00)");
        }

        // Validar duración permitida
        if (!DURACIONES_PERMITIDAS.contains(reservaRequestDTO.getDuracionMinutos())) {
            throw new RuntimeException("La duración debe ser de 30, 60, 90 o 120 minutos");
        }

        // Validar que el cliente no tenga otra reserva el mismo día
        if (reservaRepository.existsReservaByClienteAndDate(
                reservaRequestDTO.getClienteId(),
                reservaRequestDTO.getFechaHora())) {
            throw new RuntimeException("El cliente ya tiene una reserva para ese día");
        }

        // Validar solapamiento de reservas en el mismo puesto
        LocalDateTime fechaHoraFin = reservaRequestDTO.getFechaHora()
                .plusMinutes(reservaRequestDTO.getDuracionMinutos());
        if (reservaRepository.existsOverlappingReserva(
                reservaRequestDTO.getPuestoId(),
                reservaRequestDTO.getFechaHora(),
                fechaHoraFin)) {
            throw new RuntimeException("El puesto ya está reservado en ese horario");
        }
    }
}