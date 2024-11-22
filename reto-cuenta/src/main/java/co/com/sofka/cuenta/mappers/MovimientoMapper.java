package co.com.sofka.cuenta.mappers;

import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.entities.Movimiento;
import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;
import co.com.sofka.cuenta.persistence.projections.report.MovimientoProjection;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MovimientoMapper{

    public MovimientoDTO toResponseDTO(Movimiento movimiento){

        return  MovimientoDTO.builder()
                .id(movimiento.getId())
                .fecha(movimiento.getFecha())
                .saldo(movimiento.getCuenta().getSaldoDisponible().longValue())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor().longValue())
                .build();
    }

    public Movimiento requestToEntity(MovimientoRequestDTO dto){
        var cuenta = Cuenta.builder()
                .id(dto.cuentaId())
                .build();

        return Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(dto.monto().compareTo(BigDecimal.ZERO) < 0? TipoMovimiento.RETIRO: TipoMovimiento.DEPOSITO)
                .valor(dto.monto())
                .cuenta(cuenta)
                .build();
    }

    public ReporteMovimientoDTO entityToReportDTO(MovimientoProjection movimiento, ClienteDto clienteDto){

        var cuenta = movimiento.getCuenta();

        return ReporteMovimientoDTO.builder()
                .fecha(movimiento.getFecha())
                .cliente(clienteDto.nombre())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipo(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial().longValue())
                .estado(cuenta.getEstado())
                .movimiento(movimiento.getValor().longValue())
                .saldoDisponible(cuenta.getSaldoDisponible().longValue())
                .build();
    }

}
