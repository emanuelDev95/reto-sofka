package co.com.sofka.cuenta.mappers;

import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.entities.Movimiento;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class MovimientoMapper{

    public MovimientoDTO toResponseDTO(Movimiento movimiento){

        return  MovimientoDTO.builder()
                .id(movimiento.getId())
                .fecha(movimiento.getFecha().toInstant())
                .saldo(movimiento.getCuenta().getSaldo())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .build();
    }

    public Movimiento requestToEntity(MovimientoRequestDTO dto){
        var cuenta = Cuenta.builder()
                .id(dto.cuentaId())
                .build();

        return Movimiento.builder()
                .fecha(Date.from(Instant.now()))
                .tipoMovimiento(dto.tipoMovimiento())
                .valor(dto.monto())
                .cuenta(cuenta)
                .build();
    }

}
