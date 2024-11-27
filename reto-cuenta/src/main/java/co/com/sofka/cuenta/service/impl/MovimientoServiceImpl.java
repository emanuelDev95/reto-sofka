package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.exceptions.BadRequestException;
import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.exceptions.SaldoInsuficienteException;
import co.com.sofka.cuenta.mappers.MovimientoMapper;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.projections.report.MovimientoProjection;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.persistence.repositories.MovimientoRepository;
import co.com.sofka.cuenta.service.ClienteService;
import co.com.sofka.cuenta.service.MovimientoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.com.sofka.cuenta.constants.CuentaConstant.CUENTA_NO_ENCONTRADA;
import static co.com.sofka.cuenta.constants.CuentaConstant.SALDO_INSUFICIENTE;
import static co.com.sofka.cuenta.constants.MessagesGenericConstant.END_DATE_BEFORE_START_ERROR;
import static co.com.sofka.cuenta.constants.MovimientoConstant.MONTO_INCORRECTO;
import static co.com.sofka.cuenta.constants.MovimientoConstant.MOVIMIENTO_NO_ENCONTRADO;

@Service
@Transactional
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteService clienteService;
    private final MovimientoMapper movimientoMapper;


    @Override
    public MovimientoDTO save(MovimientoRequestDTO movimientoRequestDTO) {
        validarMonto(movimientoRequestDTO.monto());
        Cuenta cuenta = obtenerCuentaPorId(movimientoRequestDTO.cuentaId());

        if (movimientoRequestDTO.monto().compareTo(BigDecimal.ZERO) < 0) {
            validarSaldoDisponible(cuenta, movimientoRequestDTO.monto());
        }
        actualizarSaldoCuenta(cuenta, movimientoRequestDTO.monto());
        var movimiento = this.movimientoMapper.requestToEntity(movimientoRequestDTO);
        movimiento.setCuenta(cuenta);
        this.movimientoRepository.saveAndFlush(movimiento);
        return this.movimientoMapper.toResponseDTO(movimiento);


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

        this.validarMonto(dto.monto());
        Cuenta cuenta = obtenerCuentaPorId(dto.cuentaId());

        if (dto.monto().compareTo(BigDecimal.ZERO) < 0) {
            this.validarSaldoDisponible(cuenta, dto.monto());
        }
        var movimiento = this.movimientoMapper.requestToEntity(dto);
        movimiento.setId(id);
        this.actualizarSaldoCuenta(cuenta, dto.monto());
        movimiento.setCuenta(cuenta);
        this.movimientoRepository.saveAndFlush(movimiento);
        return this.movimientoMapper.toResponseDTO(movimiento);
    }

    @Override
    public List<ReporteMovimientoDTO> getReportByDateAndCustomer(LocalDateTime start, LocalDateTime end, Long customerId){

        if(end.isBefore(start)){
            throw new BadRequestException(END_DATE_BEFORE_START_ERROR);
        }
        var cliente =  this.clienteService.getClienteById(customerId);

        var movimientos = this.movimientoRepository.findByCuentaClienteIdAndFechaBetween(customerId, start, end,
                MovimientoProjection.class);

        return movimientos.stream()
                .map(movimiento-> this.movimientoMapper.entityToReportDTO(movimiento, cliente))
                .toList();



    }


    private void validarMonto(BigDecimal monto) {
        if (Objects.isNull(monto) || BigDecimal.ZERO.compareTo(monto) ==0) {
            throw new IllegalArgumentException(MONTO_INCORRECTO);
        }
    }

    private Cuenta obtenerCuentaPorId(Long cuentaId) {
        return this.cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new ResourceNotFoundException(CUENTA_NO_ENCONTRADA));
    }

    private void validarSaldoDisponible(Cuenta cuenta, BigDecimal monto) {
        if (cuenta.getSaldoDisponible().compareTo(monto.negate()) < 0) {
            throw new SaldoInsuficienteException(SALDO_INSUFICIENTE);
        }
    }

    private void actualizarSaldoCuenta(Cuenta cuenta, BigDecimal monto) {
        BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(monto);
        cuenta.setSaldoDisponible(nuevoSaldo);
        this.cuentaRepository.save(cuenta);
    }
}
