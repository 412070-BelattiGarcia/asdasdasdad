package ar.edu.utn.frc.tup.piii.models.service.impl;

import ar.edu.utn.frc.tup.piii.dto.ClienteDTO;
import ar.edu.utn.frc.tup.piii.models.entities.Cliente;
import ar.edu.utn.frc.tup.piii.models.repository.ClienteRepository;
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
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreCompleto("Juan Pérez");
        cliente.setEmail("juan@email.com");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombreCompleto("Juan Pérez");
        clienteDTO.setEmail("juan@email.com");
    }

    @Test
    void findAll_ShouldReturnListOfClienteDTOs() {
        // Given
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        // When
        List<ClienteDTO> result = clienteService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clienteDTO.getId(), result.get(0).getId());
        assertEquals(clienteDTO.getNombreCompleto(), result.get(0).getNombreCompleto());
        assertEquals(clienteDTO.getEmail(), result.get(0).getEmail());

        verify(clienteRepository).findAll();
        verify(modelMapper).map(cliente, ClienteDTO.class);
    }

    @Test
    void findById_WhenClienteExists_ShouldReturnClienteDTO() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        // When
        ClienteDTO result = clienteService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(clienteDTO.getId(), result.getId());
        assertEquals(clienteDTO.getNombreCompleto(), result.getNombreCompleto());
        assertEquals(clienteDTO.getEmail(), result.getEmail());

        verify(clienteRepository).findById(1L);
        verify(modelMapper).map(cliente, ClienteDTO.class);
    }

    @Test
    void findById_WhenClienteNotExists_ShouldThrowException() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.findById(1L));

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository).findById(1L);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void findByEntity_WhenClienteExists_ShouldReturnCliente() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        Cliente result = clienteService.findByEntity(1L);

        // Then
        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNombreCompleto(), result.getNombreCompleto());
        assertEquals(cliente.getEmail(), result.getEmail());

        verify(clienteRepository).findById(1L);
    }

    @Test
    void findByEntity_WhenClienteNotExists_ShouldThrowException() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.findByEntity(1L));

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository).findById(1L);
    }
}