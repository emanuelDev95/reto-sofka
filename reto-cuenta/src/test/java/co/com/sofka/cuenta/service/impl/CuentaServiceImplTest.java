package co.com.sofka.cuenta.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.models.dto.cliente.ClienteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.sofka.cuenta.mappers.CuentaMapper;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.service.ClienteService;
import co.com.sofka.cuenta.persistence.entities.TipoCuenta;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @Test
    void testSave() {
        CuentaRequestDto cuentaRequestDto = CuentaRequestDto.builder()
                .numeroCuenta("123456789")
                .tipoCuenta(TipoCuenta.AHORRO)
                .saldoInicial(BigDecimal.valueOf(1000))
                .estado(true)
                .clienteId(1L)
                .build();

        Cuenta cuenta = new Cuenta(1L, "123456789", TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), true, 1L, new ArrayList<>());

        ClienteDto clienteDto = new ClienteDto(1L, "12345", "Juan", "Masculino", 30, "Calle Ficticia 123", "555-1234", "password123", true);
        CuentaDto cuentaDto = new CuentaDto("123456789", TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000), true, "Juan");

        when(clienteService.getClienteById(1L)).thenReturn(clienteDto);
        when(cuentaMapper.cuentaToCuentaRequestDto(cuentaRequestDto)).thenReturn(cuenta);
        when(cuentaRepository.saveAndFlush(cuenta)).thenReturn(cuenta);
        when(cuentaMapper.cuentaToCuentaDto(cuenta, clienteDto)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.save(cuentaRequestDto);

        assertNotNull(result);
        assertEquals("123456789", result.numeroCuenta());
        assertEquals(TipoCuenta.AHORRO, result.tipo());
        assertEquals(BigDecimal.valueOf(1000), result.saldo());
        assertEquals(true, result.estado());
        assertEquals("Juan", result.cliente());
        verify(cuentaRepository, times(1)).saveAndFlush(any(Cuenta.class));
    }

    @Test
    void testGetById() {
        Cuenta cuenta = new Cuenta(1L, "123456789", TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), true, 1L, new ArrayList<>());

        ClienteDto clienteDto = new ClienteDto(1L, "12345", "Juan", "Masculino", 30, "Calle Ficticia 123", "555-1234", "password123", true);
        CuentaDto cuentaDto = new CuentaDto("123456789", TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000), true, "Juan");

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(clienteService.getClienteById(1L)).thenReturn(clienteDto);
        when(cuentaMapper.cuentaToCuentaDto(cuenta, clienteDto)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.getById(1L);

        assertNotNull(result);
        assertEquals("123456789", result.numeroCuenta());
        assertEquals(BigDecimal.valueOf(1000), result.saldo());
        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdThrowsResourceNotFoundException() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.getById(1L));
    }

    @Test
    void testDelete() {
        doNothing().when(cuentaRepository).deleteById(1L);

        cuentaService.delete(1L);

        verify(cuentaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        CuentaRequestDto cuentaRequestDto = CuentaRequestDto.builder()
                .numeroCuenta("123456789")
                .tipoCuenta(TipoCuenta.CORRIENTE)
                .saldoInicial(BigDecimal.valueOf(1500))
                .estado(true)
                .clienteId(1L)
                .build();

        Cuenta cuenta = new Cuenta(1L, "123456789", TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), true, 1L, new ArrayList<>());

        ClienteDto clienteDto = new ClienteDto(1L, "12345", "Juan", "Masculino", 30, "Calle Ficticia 123", "555-1234", "password123", true);
        CuentaDto cuentaDto = new CuentaDto("123456789", TipoCuenta.CORRIENTE,
                BigDecimal.valueOf(1500), true, "Juan");

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.cuentaToCuentaRequestDto(cuentaRequestDto)).thenReturn(cuenta);
        when(cuentaRepository.saveAndFlush(cuenta)).thenReturn(cuenta);
        when(clienteService.getClienteById(1L)).thenReturn(clienteDto);
        when(cuentaMapper.cuentaToCuentaDto(cuenta, clienteDto)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.update(cuentaRequestDto, 1L);

        assertNotNull(result);
        assertEquals("123456789", result.numeroCuenta());
        assertEquals(TipoCuenta.CORRIENTE, result.tipo());
        assertEquals(BigDecimal.valueOf(1500), result.saldo());
        assertEquals(true, result.estado());
        assertEquals("Juan", result.cliente());
        verify(cuentaRepository, times(1)).saveAndFlush(cuenta);
    }

    @Test
    void testUpdateThrowsResourceNotFoundException() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.update(
                CuentaRequestDto.builder()
                        .numeroCuenta("123456789")
                        .tipoCuenta(TipoCuenta.AHORRO)
                        .saldoInicial(BigDecimal.valueOf(1000))
                        .estado(true)
                        .clienteId(1L)
                        .build(), 1L));
    }
}
