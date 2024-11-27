package co.com.sofka.cuenta.controllers.rest;

import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;
import co.com.sofka.cuenta.persistence.entities.TipoMovimiento;
import co.com.sofka.cuenta.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimientoController.class)
public class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;


    @Test
    public void testRegistrarMovimiento() throws Exception {
        MovimientoRequestDTO requestDto = new MovimientoRequestDTO(1L, BigDecimal.valueOf(1000));
        MovimientoDTO responseDto = MovimientoDTO.builder()
                .id(1L)
                .fecha(LocalDateTime.now())
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(1000L)
                .saldo(2000L)
                .build();
        when(movimientoService.save(any(MovimientoRequestDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cuentaId\":1, \"monto\":1000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Almacenado Exitosamente"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.tipoMovimiento").value("DEPOSITO"))
                .andExpect(jsonPath("$.data.valor").value(1000L))
                .andExpect(jsonPath("$.data.saldo").value(2000L));
    }

    @Test
    public void testGetAll() throws Exception {
        List<MovimientoDTO> movimientoList = Arrays.asList(
                MovimientoDTO.builder().id(1L).fecha(LocalDateTime.now()).tipoMovimiento(TipoMovimiento.DEPOSITO).valor(1000L).saldo(2000L).build(),
                MovimientoDTO.builder().id(2L).fecha(LocalDateTime.now()).tipoMovimiento(TipoMovimiento.RETIRO).valor(500L).saldo(1500L).build()
        );
        when(movimientoService.getAll()).thenReturn(movimientoList);

        mockMvc.perform(get("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
    }

    @Test
    public void testGetById() throws Exception {
        MovimientoDTO movimientoDto = MovimientoDTO.builder()
                .id(1L)
                .fecha(LocalDateTime.now())
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(1000L)
                .saldo(2000L)
                .build();
        when(movimientoService.getById(anyLong())).thenReturn(movimientoDto);

        mockMvc.perform(get("/api/movimientos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.tipoMovimiento").value("DEPOSITO"))
                .andExpect(jsonPath("$.data.valor").value(1000L))
                .andExpect(jsonPath("$.data.saldo").value(2000L));
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/api/movimientos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado Exitosamente"));
    }

    @Test
    public void testUpdate() throws Exception {
        MovimientoRequestDTO requestDto = new MovimientoRequestDTO(1L, BigDecimal.valueOf(1000));
        MovimientoDTO responseDto = MovimientoDTO.builder()
                .id(1L)
                .fecha(LocalDateTime.now())
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(1000L)
                .saldo(2000L)
                .build();
        when(movimientoService.update(any(MovimientoRequestDTO.class), anyLong())).thenReturn(responseDto);

        mockMvc.perform(put("/api/movimientos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cuentaId\":1, \"monto\":1000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.tipoMovimiento").value("DEPOSITO"))
                .andExpect(jsonPath("$.data.valor").value(1000L))
                .andExpect(jsonPath("$.data.saldo").value(2000L));
    }

    @Test
    public void testGetReporte() throws Exception {
        List<ReporteMovimientoDTO> reporteList = Arrays.asList(
                ReporteMovimientoDTO.builder().fecha(new Date()).cliente("Cliente1").numeroCuenta("123456").tipo("DEPOSITO").saldoInicial(1000L).estado(true).movimiento(1000L).saldoDisponible(2000L).build(),
                ReporteMovimientoDTO.builder().fecha(new Date()).cliente("Cliente2").numeroCuenta("654321").tipo("RETIRO").saldoInicial(2000L).estado(false).movimiento(500L).saldoDisponible(1500L).build()
        );
        when(movimientoService.getReportByDateAndCustomer(any(LocalDateTime.class), any(LocalDateTime.class), anyLong())).thenReturn(reporteList);

        mockMvc.perform(get("/api/movimientos/reporte")
                        .param("startDate", "2023-01-01T00:00:00")
                        .param("endDate", "2023-12-31T23:59:59")
                        .param("cliente", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].cliente").value("Cliente1"))
                .andExpect(jsonPath("$.data[1].cliente").value("Cliente2"));
    }
}
