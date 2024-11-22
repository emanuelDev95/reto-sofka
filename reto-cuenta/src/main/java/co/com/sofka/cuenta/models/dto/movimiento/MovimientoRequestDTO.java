package co.com.sofka.cuenta.models.dto.movimiento;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        Long cuentaId,
        BigDecimal monto
) {

}
