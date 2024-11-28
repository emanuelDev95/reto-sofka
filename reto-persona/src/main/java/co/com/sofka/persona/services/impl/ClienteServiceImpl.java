package co.com.sofka.persona.services.impl;

import co.com.sofka.persona.events.ClientEvent;
import co.com.sofka.persona.mappers.ClientMapper;
import co.com.sofka.persona.models.dto.ClienteCuentaDto;
import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.persistence.entities.Cliente;
import co.com.sofka.persona.persistence.repository.ClienteRepository;
import co.com.sofka.persona.services.ClienteService;
import co.com.sofka.persona.services.commons.AbstractService;
import co.com.sofka.persona.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ClienteServiceImpl extends AbstractService<Cliente, ClienteDto, Long> implements ClienteService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ClientMapper mapper;
    private static final String NOT_FOUND_STATUS_MESSAGE = "Cliente no encontrado";

    protected ClienteServiceImpl(ClienteRepository repository, ClientMapper mapper, KafkaTemplate<String, String> kafkaTemplate) {
        super(repository, mapper, NOT_FOUND_STATUS_MESSAGE);
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ClienteDto saveClienteCuenta(ClienteCuentaDto dto) {
        var entity = this.mapper.toEntity(dto);
        this.repository.saveAndFlush(entity);

        try {
            ClientEvent clientEvent = crearEventoDesdeDto(dto, entity);
            String jsonMessage = JsonUtils.toJson(clientEvent);

            // Enviar mensaje a Kafka
            this.kafkaTemplate.send("cliente-topic", jsonMessage);

        } catch (Exception e) {
            log.error("Error al procesar el mensaje para Kafka", e);
        }

        return this.mapper.toDto(entity);
    }

    private ClientEvent crearEventoDesdeDto(ClienteCuentaDto dto, Cliente cliente) {
        return new ClientEvent(dto.numeroCuenta(), dto.tipo(), dto.saldo(), dto.estadoCuenta(), cliente.getId());
    }
}
