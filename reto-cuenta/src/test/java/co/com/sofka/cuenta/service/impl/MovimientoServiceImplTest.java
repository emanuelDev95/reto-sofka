package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.exceptions.SaldoInsuficienteException;
import co.com.sofka.cuenta.mappers.MovimientoMapper;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;
import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.entities.Movimiento;
import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;
import co.com.sofka.cuenta.persistence.projections.report.CuentaProjection;
import co.com.sofka.cuenta.persistence.projections.report.MovimientoProjection;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.persistence.repositories.MovimientoRepository;
import co.com.sofka.cuenta.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class MovimientoServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private MovimientoMapper movimientoMapper;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Cuenta cuenta;
    private MovimientoRequestDTO movimientoRequestDTO;
    private MovimientoDTO movimientoDTO;

    @BeforeEach
     void setUp() {
        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setSaldoDisponible(BigDecimal.valueOf(1000));

        movimientoRequestDTO = new MovimientoRequestDTO(1L, BigDecimal.valueOf(500));
        movimientoDTO = new MovimientoDTO(1L, LocalDateTime.now(), TipoMovimiento.DEPOSITO, 500L, 1500L);
    }

    @Test
     void testSave() {
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));
        when(movimientoMapper.requestToEntity(any(MovimientoRequestDTO.class))).thenReturn(new Movimiento());
        when(movimientoMapper.toResponseDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        MovimientoDTO result = movimientoService.save(movimientoRequestDTO);

        assertNotNull(result);
        assertEquals(500L, result.valor());
        verify(movimientoRepository, times(1)).saveAndFlush(any(Movimiento.class));
    }

    @Test
     void testGetAll() {
        when(movimientoRepository.findAll()).thenReturn(List.of(new Movimiento(), new Movimiento()));
        when(movimientoMapper.toResponseDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        List<MovimientoDTO> result = movimientoService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
     void testGetById() {
        when(movimientoRepository.findById(anyLong())).thenReturn(Optional.of(new Movimiento()));
        when(movimientoMapper.toResponseDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        MovimientoDTO result = movimientoService.getById(1L);

        assertNotNull(result);
        assertEquals(500L, result.valor());
    }

    @Test
     void testDelete() {
        doNothing().when(movimientoRepository).deleteById(anyLong());

        movimientoService.delete(1L);

        verify(movimientoRepository, times(1)).deleteById(anyLong());
    }

    @Test
     void testUpdate() {
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));
        when(movimientoMapper.requestToEntity(any(MovimientoRequestDTO.class))).thenReturn(new Movimiento());
        when(movimientoMapper.toResponseDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        MovimientoDTO result = movimientoService.update(movimientoRequestDTO, 1L);

        assertNotNull(result);
        assertEquals(500L, result.valor());
        verify(movimientoRepository, times(1)).saveAndFlush(any(Movimiento.class));
    }

    @Test
     void testGetReportByDateAndCustomer() {
        when(clienteService.getClienteById(anyLong())).thenReturn(new ClienteDto(1L, "123456", "Cliente1", "M", 30, "Direccion", "123456789", "password", true));
        when(movimientoRepository.findByCuentaClienteIdAndFechaBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), eq(MovimientoProjection.class)))
                .thenReturn(List.of(getMovimientoProjection()));


        when(movimientoMapper.entityToReportDTO(any(MovimientoProjection.class), any(ClienteDto.class))).thenReturn(ReporteMovimientoDTO.builder()
                .fecha(new Date())
                .cliente("Cliente1")
                .numeroCuenta("123456")
                .tipo("DEPOSITO")
                .saldoInicial(1000L)
                .estado(true)
                .movimiento(500L)
                .saldoDisponible(1500L)
                .build());

        List<ReporteMovimientoDTO> result = movimientoService.getReportByDateAndCustomer(LocalDateTime.now().minusDays(1), LocalDateTime.now(), 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
     void testValidarMontoNoValido() {
        assertThrows(IllegalArgumentException.class, () -> movimientoService.save(new MovimientoRequestDTO(1L, BigDecimal.ZERO)));
    }

    @Test
     void testObtenerCuentaPorIdNotFound() {
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movimientoService.save(movimientoRequestDTO));
    }

    @Test
     void testValidarSaldoNoDisponible() {
        cuenta.setSaldoDisponible(BigDecimal.valueOf(100));
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.save(new MovimientoRequestDTO(1L, BigDecimal.valueOf(-500))));
    }

    @Test
     void testActualizarSaldoCuenta() {
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));
        when(movimientoMapper.requestToEntity(any(MovimientoRequestDTO.class))).thenReturn(new Movimiento());
        when(movimientoMapper.toResponseDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        movimientoService.save(movimientoRequestDTO);

        assertEquals(BigDecimal.valueOf(1500), cuenta.getSaldoDisponible());
    }

    @Test

    /**Metodo pra obtener movimiento**/

    private MovimientoProjection getMovimientoProjection() {
        return new MovimientoProjection() {
            @Override
            public Date getFecha() {
                return new Date();
            }

            @Override
            public BigDecimal getValor() {
                return BigDecimal.valueOf(500);
            }

            @Override
            public CuentaProjection getCuenta() {
                return new CuentaProjection() {
                    @Override
                    public String getNumeroCuenta() {
                        return "123456";
                    }

                    @Override
                    public String getTipoCuenta() {
                        return "AHORRO";
                    }

                    @Override
                    public Boolean getEstado() {
                        return true;
                    }

                    @Override
                    public BigDecimal getSaldoInicial() {
                        return BigDecimal.valueOf(1000);
                    }

                    @Override
                    public BigDecimal getSaldoDisponible() {
                        return BigDecimal.valueOf(1500);
                    }
                };
            }
        };
    }
}
