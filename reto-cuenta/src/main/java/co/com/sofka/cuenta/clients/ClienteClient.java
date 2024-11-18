package co.com.sofka.cuenta.clients;

import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.models.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ClienteClient {

    @GetExchange("/{id}")
    ResponseEntity<MessageResponse<ClienteDto>> getById(@PathVariable @NonNull Long id);
}
