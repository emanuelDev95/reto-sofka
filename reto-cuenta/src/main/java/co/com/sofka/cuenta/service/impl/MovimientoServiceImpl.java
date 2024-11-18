package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.exceptions.SaldoInsuficienteException;
import co.com.sofka.cuenta.mappers.MovimientoMapper;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.persistence.repositories.MovimientoRepository;
import co.com.sofka.cuenta.service.MovimientoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static co.com.sofka.cuenta.constants.CuentaConstant.CUENTA_NO_ENCONTRADA;
import static co.com.sofka.cuenta.constants.CuentaConstant.SALDO_INSUFICIENTE;
import static co.com.sofka.cuenta.constants.MovimientoConstant.MONTO_INCORRECTO;
import static co.com.sofka.cuenta.constants.MovimientoConstant.MOVIMIENTO_NO_ENCONTRADO;

@Service
@Transactional
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;


    @Override
    public MovimientoDTO save(MovimientoRequestDTO movimientoRequestDTO) {
        validarMonto(movimientoRequestDTO.monto());
        Cuenta cuenta = obtenerCuentaPorId(movimientoRequestDTO.cuentaId());

        if (movimientoRequestDTO.monto().compareTo(BigDecimal.ZERO) < 0) {
            validarSaldoDisponible(cuenta, movimientoRequestDTO.monto());
        }
        var movimiento = movimientoMapper.requestToEntity(movimientoRequestDTO);
        actualizarSaldoCuenta(cuenta, movimientoRequestDTO.monto());
        return movimientoMapper.toResponseDTO(movimiento);


    }

    @Override
    public List<MovimientoDTO> getAll() {
        return this.movimientoRepository.findAll()
                .stream().map(this.movimientoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public MovimientoDTO getById(Long id) {
        return this.movimientoRepository.findById(id)
                .map(this.movimientoMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIMIENTO_NO_ENCONTRADO));
    }

    @Override
    public void delete(Long id) {
        this.movimientoRepository.deleteById(id);
    }

    @Override
    public MovimientoDTO update(MovimientoRequestDTO dto, Long id) {

        validarMonto(dto.monto());
        Cuenta cuenta = obtenerCuentaPorId(dto.cuentaId());

        if (dto.monto().compareTo(BigDecimal.ZERO) < 0) {
            validarSaldoDisponible(cuenta, dto.monto());
        }
        var movimiento = movimientoMapper.requestToEntity(dto);
        movimiento.setId(id);
        movimientoRepository.save(movimiento);
        actualizarSaldoCuenta(cuenta, dto.monto());
        return movimientoMapper.toResponseDTO(movimiento);
    }


    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(MONTO_INCORRECTO);
        }
    }

    private Cuenta obtenerCuentaPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new ResourceNotFoundException(CUENTA_NO_ENCONTRADA));
    }

    private void validarSaldoDisponible(Cuenta cuenta, BigDecimal monto) {
        if (cuenta.getSaldo().compareTo(monto.negate()) < 0) {
            throw new SaldoInsuficienteException(SALDO_INSUFICIENTE);
        }
    }

    private void actualizarSaldoCuenta(Cuenta cuenta, BigDecimal monto) {
        BigDecimal nuevoSaldo = cuenta.getSaldo().add(monto);
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
    }
}
