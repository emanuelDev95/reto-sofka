package co.com.sofka.cuenta.models.dto.movimiento;

import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MovimientoDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDateTime fecha,
        TipoMovimiento tipoMovimiento,
        Long valor,
        Long saldo
) {
}
