package co.com.sofka.cuenta.models.dto.movimiento;

import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        Long cuentaId,
        BigDecimal monto,
        TipoMovimiento tipoMovimiento
) {

}
