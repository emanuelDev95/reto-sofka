package co.com.sofka.cuenta.listeners;

import co.com.sofka.cuenta.mappers.CuentaMapper;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CuentaListener {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @KafkaListener(topics = "cliente-topic", groupId = "cuenta-consumer-group")
    public void listen(String message) {
        try {
            // Deserializar el mensaje recibido de Kafka
            var cuentaDto = JsonUtils.fromJson(message, CuentaRequestDto.class);
            log.info("Mensaje recibido: {}", cuentaDto);

            // Mapear el DTO a la entidad Cuenta
            var cuenta = cuentaMapper.cuentaToCuentaRequestDto(cuentaDto);  // Suponiendo que 'cuentaRequestDtoToCuenta' existe
            log.info("Cuenta mapeada: {}", cuenta);

            // Guardar la cuenta en la base de datos
            cuentaRepository.saveAndFlush(cuenta);
            log.info("Cuenta guardada en base de datos: {}", cuenta);

        } catch (Exception e) {
            log.error("Error procesando el mensaje: {}", message, e);
        }
    }
}
