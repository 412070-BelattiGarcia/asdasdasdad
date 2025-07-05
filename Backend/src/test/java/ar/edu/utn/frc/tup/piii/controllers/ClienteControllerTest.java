package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dto.ClienteDTO;
import ar.edu.utn.frc.tup.piii.models.service.interfaces.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void getAllClientes() {
        // Arranque
        ClienteDTO c1 = new ClienteDTO(); c1.setId(1L); c1.setNombreCompleto("A");
        ClienteDTO c2 = new ClienteDTO(); c2.setId(2L); c2.setNombreCompleto("B");
        when(clienteService.findAll()).thenReturn(Arrays.asList(c1, c2));

        // Ejecución
        ResponseEntity<List<ClienteDTO>> resp = clienteController.getAllClientes();

        // Verificaciones
        assertEquals(200, resp.getStatusCodeValue());
        List<ClienteDTO> body = resp.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(1L, body.get(0).getId());
        verify(clienteService).findAll();
    }

    @Test
    void getClienteById() {
        // Arranque
        ClienteDTO dto = new ClienteDTO(); dto.setId(1L); dto.setNombreCompleto("A");
        when(clienteService.findById(1L)).thenReturn(dto);

        // Ejecución
        ResponseEntity<ClienteDTO> resp = clienteController.getClienteById(1L);

        // Verificaciones
        assertEquals(200, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("A", resp.getBody().getNombreCompleto());
        verify(clienteService).findById(1L);
    }
}
