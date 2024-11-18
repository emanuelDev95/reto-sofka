package co.com.sofka.cuenta.mappers;

import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper  {

    public CuentaRequestDto cuentaToCuentaRequestDto(Cuenta cuenta) {

        return CuentaRequestDto.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldo())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }

    public Cuenta cuentaToCuentaRequestDto(CuentaRequestDto cuentaRequestDto ) {

        return Cuenta.builder()
                .numeroCuenta(cuentaRequestDto.numeroCuenta())
                .tipoCuenta(cuentaRequestDto.tipoCuenta())
                .saldo(cuentaRequestDto.saldoInicial())
                .estado(cuentaRequestDto.estado())
                .clienteId(cuentaRequestDto.clienteId())
                .build();
    }

    public CuentaDto cuentaToCuentaDto(Cuenta cuenta, ClienteDto clienteDto) {

        return  CuentaDto.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipo(cuenta.getTipoCuenta())
                .saldo(cuenta.getSaldo())
                .estado(cuenta.getEstado())
                .cliente(clienteDto.nombre())
                .build();

    }

}
