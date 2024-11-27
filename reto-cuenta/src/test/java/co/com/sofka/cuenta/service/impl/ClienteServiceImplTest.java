package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.clients.ClienteClient;
import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.models.response.MessageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void testGetClienteById_ReturnsClienteDto() {
        Long clienteId = 1L;
        ClienteDto expectedCliente = new ClienteDto(
                clienteId, "1234567890", "Juan Pérez", "Masculino", 30, "Calle Falsa 123", "123456789", "password123", true
        );

        MessageResponse<ClienteDto> expectedResponse = MessageResponse.<ClienteDto>builder()
                .message("OK")
                .statusCode(200)
                .data(expectedCliente)
                .build();

        ResponseEntity<MessageResponse<ClienteDto>> response = ResponseEntity.ok(expectedResponse);


        when(clienteClient.getById(anyLong())).thenReturn(response);
        
        ClienteDto actualResponse = clienteService.getClienteById(clienteId);
        
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.message(), "OK");
        assertEquals(expectedResponse.statusCode(), 200);
        assertEquals(expectedResponse.data(), actualResponse);
    }


}
