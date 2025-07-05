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

import java.time.LocalDate;
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
    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaServiceImpl(
            ModelMapper modelMapper,
            ClienteService clienteService,
            PuestoJuegoService puestoJuegoService,
            VideoJuegoService videoJuegoService,
            ReservaService reservaService, ReservaRepository reservaRepository){
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
        this.puestoJuegoService = puestoJuegoService;
        this.videoJuegoService = videoJuegoService;
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
    }

    private static final LocalTime HORA_APERTURA = LocalTime.of(10, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(22, 0);
    private static final List<Integer> DURACIONES_PERMITIDAS = Arrays.asList(30, 60, 90, 120);


    @Override
    public List<ReservaDTO> findAll() {
        return reservaService.findAll().stream()
                .map(reserva -> modelMapper.map(reserva,ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> findByFilter(Long clienteId, Long videoJuegoId, Long puestoJuegoId, LocalDateTime fechaHora) {
        List<Reserva> reservas = reservaRepository.findByFilters(clienteId, videoJuegoId, puestoJuegoId,fechaHora);
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva,ReservaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO findById(Long id) {
        ReservaDTO reserva = reservaService.findById(id).ma
    }

    @Override
    public ReservaDTO create(ReservaRequestDTO reservaRequestDTO) {

        validarReserva(reservaRequestDTO);

        Cliente cliente = clienteService.findByEntity(reservaRequestDTO.getClienteId());
        Videojuego videojuego = videoJuegoService.findByEntity(reservaRequestDTO.getVideojuegoId());
        PuestoJuego puestoJuego = puestoJuegoService.findByEntity(reservaRequestDTO.getPuestoId());

        ReservaRequestDTO reserva = new ReservaRequestDTO();
        reserva.setClienteId(cliente.getId());
        reserva.setVideojuegoId(videojuego.getId());
        reserva.setPuestoId(puestoJuego.getId());
        reserva.setFechaHora(reservaRequestDTO.getFechaHora());
        reserva.setDuracionMinutos(reservaRequestDTO.getDuracionMinutos());
        reserva.setObservaciones(reservaRequestDTO.getObservaciones());

        ReservaDTO reservaGuardada = reservaService.create(reserva);

        return modelMapper.map(reserva,ReservaDTO.class);
    }

    private void validarReserva(ReservaRequestDTO reservaRequestDTO) {

        LocalTime horaReserva = reservaRequestDTO.getFechaHora().toLocalTime();
        LocalTime horaFin = horaReserva.plusMinutes(reservaRequestDTO.getDuracionMinutos());

        if (horaReserva.isBefore(HORA_APERTURA) || horaFin.isAfter(HORA_CIERRE)) {
            throw new RuntimeException("La reserva debe estar dentro del horario permitido (10:00 - 22:00");
        }

        if (!DURACIONES_PERMITIDAS.contains(reservaRequestDTO.getDuracionMinutos())) {
            throw new RuntimeException("La duración debe ser de 30, 60, 90, 120 minutos");
        }

        if (reservaRepository.existsReservaByClienteAndDate(
                reservaRequestDTO.getClienteId(),
                reservaRequestDTO.getFechaHora())){
            throw new RuntimeException("El cliente ya tiene una reserva para ese día");
        }

        LocalDateTime fechaHoraFin = reservaRequestDTO.getFechaHora()
                .plusMinutes(reservaRequestDTO.getDuracionMinutos());
        if (reservaRepository.existsOverlappingReserva(
                reservaRequestDTO.getPuestoId(),
                reservaRequestDTO.getFechaHora(),
                fechaHoraFin
        )){
            throw new RuntimeException("El puesto ya está reservado en ese horario");
        }

    }

}
