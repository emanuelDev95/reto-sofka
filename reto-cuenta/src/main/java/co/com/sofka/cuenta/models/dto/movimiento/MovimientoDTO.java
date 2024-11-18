package co.com.sofka.cuenta.models.dto.movimiento;

import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record MovimientoDTO(
        Long id,
        Instant fecha,
        TipoMovimiento tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo
) {
}
