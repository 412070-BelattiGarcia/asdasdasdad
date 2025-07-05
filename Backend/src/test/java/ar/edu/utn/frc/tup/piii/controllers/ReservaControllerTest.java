package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.ReservaDTO;
import ar.edu.utn.frc.tup.piii.dto.ReservaRequestDTO;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

    @Test
    void getAllReservas_withoutFilters() {
        ReservaDTO r = new ReservaDTO(); r.setId(10L);
        when(reservaService.findAll()).thenReturn(Collections.singletonList(r));

        ResponseEntity<List<ReservaDTO>> resp =
                reservaController.getAllReservas(null, null, null, null);

        assertEquals(200, resp.getStatusCodeValue());
        List<ReservaDTO> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        verify(reservaService).findAll();
    }

    @Test
    void getAllReservas_withFilter() {
        ReservaDTO r = new ReservaDTO(); r.setId(11L);
        LocalDateTime fecha = LocalDateTime.now();
        when(reservaService.findByFilter(2L, 3L, 4L, fecha))
                .thenReturn(Arrays.asList(r));

        ResponseEntity<List<ReservaDTO>> resp =
                reservaController.getAllReservas(2L, 3L, 4L, fecha);

        assertEquals(200, resp.getStatusCodeValue());
        List<ReservaDTO> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        verify(reservaService).findByFilter(2L, 3L, 4L, fecha);
    }

    @Test
    void getReservaById() {
        ReservaDTO dto = new ReservaDTO(); dto.setId(20L);
        when(reservaService.findById(20L)).thenReturn(dto);

        ResponseEntity<ReservaDTO> resp = reservaController.getReservaById(20L);

        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals(20L, resp.getBody().getId());
        verify(reservaService).findById(20L);
    }

    @Test
    void createReserva() {
        ReservaRequestDTO req = new ReservaRequestDTO();
        req.setClienteId(5L);
        req.setVideojuegoId(6L);
        req.setPuestoId(7L);
        req.setFechaHora(LocalDateTime.of(2025,7,5,12,0));
        req.setDuracionMinutos(60);
        req.setObservaciones("Obs");
        ReservaDTO dto = new ReservaDTO(); dto.setId(30L);

        when(reservaService.create(req)).thenReturn(dto);

        ResponseEntity<ReservaDTO> resp = reservaController.createReserva(req);

        assertEquals(201, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals(30L, resp.getBody().getId());
        verify(reservaService).create(req);
    }
}
