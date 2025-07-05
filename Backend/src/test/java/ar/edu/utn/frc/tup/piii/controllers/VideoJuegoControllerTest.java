package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.VideojuegoDTO;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.VideoJuegoService;
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
class VideoJuegoControllerTest {

    @Mock
    private VideoJuegoService videoService;

    @InjectMocks
    private VideoJuegoController videoController;

    @Test
    void getAllVideojuegos() {
        VideojuegoDTO v = new VideojuegoDTO(); v.setId(9L); v.setTitulo("Z");
        when(videoService.findAll()).thenReturn(Collections.singletonList(v));

        ResponseEntity<List<VideojuegoDTO>> resp = videoController.getAllVideojuegos();

        assertEquals(200, resp.getStatusCodeValue());
        List<VideojuegoDTO> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        verify(videoService).findAll();
    }

    @Test
    void getVideojuegoById() {
        VideojuegoDTO dto = new VideojuegoDTO(); dto.setId(8L); dto.setTitulo("W");
        when(videoService.findById(8L)).thenReturn(dto);

        ResponseEntity<VideojuegoDTO> resp = videoController.getVideojuegoById(8L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(8L, resp.getBody().getId());
        verify(videoService).findById(8L);
    }
}
