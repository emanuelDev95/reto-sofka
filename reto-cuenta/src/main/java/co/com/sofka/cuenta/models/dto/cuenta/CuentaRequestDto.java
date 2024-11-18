package co.com.sofka.cuenta.models.dto.cuenta;

import co.com.sofka.cuenta.persistence.entities.TipoCuenta;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CuentaRequestDto(
         String numeroCuenta,
         TipoCuenta tipoCuenta,
         BigDecimal saldoInicial,
         Boolean estado,
         Long clienteId
) {
}
