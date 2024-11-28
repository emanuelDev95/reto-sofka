package co.com.sofka.persona.events;

import java.math.BigDecimal;

public record ClientEvent(
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        Long clienteId
) {
}
