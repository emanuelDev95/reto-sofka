package co.com.sofka.cuenta.models.dto.movimiento;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record ReporteMovimientoDTO(
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date fecha,
        String cliente,
        String numeroCuenta,
        String tipo,
        Long saldoInicial,
        Boolean estado,
        Long movimiento,
        Long saldoDisponible

) {
}
