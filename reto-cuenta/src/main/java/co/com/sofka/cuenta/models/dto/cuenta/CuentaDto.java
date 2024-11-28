package co.com.sofka.cuenta.models.dto.cuenta;

import co.com.sofka.cuenta.persistence.entities.TipoCuenta;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CuentaDto(
        Long id,
        String numeroCuenta,
        TipoCuenta tipo,
        BigDecimal saldo,
        Boolean estado,
        String cliente

) {
}
