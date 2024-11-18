package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.clients.ClienteClient;
import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteClient clienteClient;


    @Override
    public ClienteDto getClienteById(Long id) {
        return Objects.requireNonNull(clienteClient.getById(id).getBody()).data();
    }
}
