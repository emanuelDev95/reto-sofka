package co.com.sofka.persona.services.impl;

import co.com.sofka.persona.mappers.ClientMapper;
import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.persistence.entities.Cliente;
import co.com.sofka.persona.persistence.repository.ClienteRepository;
import co.com.sofka.persona.services.ClienteService;
import co.com.sofka.persona.services.commons.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends AbstractService<Cliente, ClienteDto, Long> implements ClienteService {


    private static final String NOT_FOUND_STATUS_MESSAGE = "Cliente no encontrado";

    protected ClienteServiceImpl(ClienteRepository repository, ClientMapper mapper) {
        super(repository, mapper, NOT_FOUND_STATUS_MESSAGE);
    }
}
