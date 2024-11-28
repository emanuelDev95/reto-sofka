package co.com.sofka.cuenta.controllers.rest;

import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.persistence.entities.TipoCuenta;
import co.com.sofka.cuenta.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;



    @Test
    void testSave() throws Exception {
        CuentaDto responseDto = new CuentaDto(
                1L,
                "123456",
                TipoCuenta.AHORRO,
                BigDecimal.valueOf(1000),
                true,
                "Cliente1");

        when(cuentaService.save(any(CuentaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroCuenta\":\"123456\", \"tipoCuenta\":\"AHORRO\", \"saldoInicial\":1000, \"estado\":true, \"clienteId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Almacenado Exitosamente"))
                .andExpect(jsonPath("$.data.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.data.tipo").value("AHORRO"))
                .andExpect(jsonPath("$.data.saldo").value(1000))
                .andExpect(jsonPath("$.data.estado").value(true))
                .andExpect(jsonPath("$.data.cliente").value("Cliente1"));
    }

    @Test
    void testGetAll() throws Exception {
        List<CuentaDto> cuentaList = Arrays.asList(
                new CuentaDto(1L,"123456", TipoCuenta.AHORRO, BigDecimal.valueOf(1000), true, "Cliente1"),
                new CuentaDto(2L,"654321", TipoCuenta.CORRIENTE, BigDecimal.valueOf(2000), false, "Cliente2")
        );
        when(cuentaService.getAll()).thenReturn(cuentaList);

        mockMvc.perform(get("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.data[1].numeroCuenta").value("654321"));
    }

    @Test
    void testGetById() throws Exception {
        CuentaDto cuentaDto = new CuentaDto(1L,"123456", TipoCuenta.AHORRO, BigDecimal.valueOf(1000), true, "Cliente1");
        when(cuentaService.getById(anyLong())).thenReturn(cuentaDto);

        mockMvc.perform(get("/api/cuentas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.data.tipo").value("AHORRO"))
                .andExpect(jsonPath("$.data.saldo").value(1000))
                .andExpect(jsonPath("$.data.estado").value(true))
                .andExpect(jsonPath("$.data.cliente").value("Cliente1"));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/api/cuentas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado Exitosamente"));
    }

    @Test
    void testUpdate() throws Exception {
        CuentaDto responseDto = new CuentaDto(1L,"123456", TipoCuenta.AHORRO, BigDecimal.valueOf(1000), true, "Cliente1");
        when(cuentaService.update(any(CuentaRequestDto.class), anyLong())).thenReturn(responseDto);

        mockMvc.perform(put("/api/cuentas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroCuenta\":\"123456\", \"tipoCuenta\":\"AHORRO\", \"saldoInicial\":1000, \"estado\":true, \"clienteId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.data.tipo").value("AHORRO"))
                .andExpect(jsonPath("$.data.saldo").value(1000))
                .andExpect(jsonPath("$.data.estado").value(true))
                .andExpect(jsonPath("$.data.cliente").value("Cliente1"));
    }
}
