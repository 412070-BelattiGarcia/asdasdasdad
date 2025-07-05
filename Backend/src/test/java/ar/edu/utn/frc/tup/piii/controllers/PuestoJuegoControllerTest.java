package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.PuestoJuegoDTO;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.PuestoJuegoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PuestoJuegoControllerTest {

    @Mock
    private PuestoJuegoService puestoService;

    @InjectMocks
    private PuestoJuegoController puestoController;

    @Test
    void getAllPuestos() {
        PuestoJuegoDTO p = new PuestoJuegoDTO(); p.setId(5L); p.setNombre("X");
        when(puestoService.findAll()).thenReturn(Collections.singletonList(p));

        ResponseEntity<List<PuestoJuegoDTO>> resp = puestoController.getAllPuestos();

        assertEquals(200, resp.getStatusCodeValue());
        List<PuestoJuegoDTO> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        assertEquals(5L, body.get(0).getId());
        verify(puestoService).findAll();
    }

    @Test
    void getPuestoById() {
        PuestoJuegoDTO dto = new PuestoJuegoDTO(); dto.setId(7L); dto.setNombre("Y");
        when(puestoService.findById(7L)).thenReturn(dto);

        ResponseEntity<PuestoJuegoDTO> resp = puestoController.getPuestoById(7L);

        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Y", resp.getBody().getNombre());
        verify(puestoService).findById(7L);
    }
}
