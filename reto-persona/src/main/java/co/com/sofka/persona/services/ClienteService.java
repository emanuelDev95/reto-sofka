package co.com.sofka.persona.services;

import co.com.sofka.persona.models.dto.ClienteCuentaDto;
import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.services.commons.GenericService;

public interface ClienteService extends GenericService<ClienteDto, Long> {

    ClienteDto saveClienteCuenta(ClienteCuentaDto dto);
}
