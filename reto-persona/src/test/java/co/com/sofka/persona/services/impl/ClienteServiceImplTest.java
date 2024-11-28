package co.com.sofka.persona.services.impl;

import co.com.sofka.persona.exceptions.ResourceNotFoundException;
import co.com.sofka.persona.mappers.ClientMapper;
import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.persistence.entities.Cliente;
import co.com.sofka.persona.persistence.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDto clienteDto;

    @BeforeEach
     void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");

        clienteDto = ClienteDto.builder()
                .id(1L)
                .identificacion("123456")
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .direccion("Calle 123")
                .telefono("123456789")
                .contrasena("password")
                .estado(true)
                .build();
    }

    @Test
     void testSave() {
        when(clienteRepository.saveAndFlush(any(Cliente.class))).thenReturn(cliente);
        when(clientMapper.toEntity(any(ClienteDto.class))).thenReturn(cliente);
        when(clientMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        ClienteDto result = clienteService.save(clienteDto);

        assertNotNull(result);
        assertEquals("Juan", result.nombre());
        verify(clienteRepository, times(1)).saveAndFlush(any(Cliente.class));
    }

    @Test
     void testGetById() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clientMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        ClienteDto result = clienteService.getById(1L);

        assertNotNull(result);
        assertEquals("Juan", result.nombre());
    }

    @Test
     void testDelete() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
     void testUpdate() {

        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(clientMapper.toEntity(any(ClienteDto.class))).thenReturn(cliente);
        when(clienteRepository.saveAndFlush(any(Cliente.class))).thenReturn(cliente);
        when(clientMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        ClienteDto result = clienteService.update(clienteDto, 1L);

        assertNotNull(result);
        assertEquals("Juan", result.nombre());
        verify(clienteRepository, times(1)).saveAndFlush(any(Cliente.class));
    }
    
    
    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clientMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        List<ClienteDto> result = clienteService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetByIdNotFound() {

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getById(1L));

    }

    @Test
    void testUpdateNotFound() {

        when(clienteRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clienteService.update(clienteDto, 1L));

    }


    
}
