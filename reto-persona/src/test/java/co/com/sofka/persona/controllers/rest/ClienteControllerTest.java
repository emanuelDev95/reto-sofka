package co.com.sofka.persona.controllers.rest;

import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.models.response.MessageResponse;
import co.com.sofka.persona.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;


    @Test
    public void testSave() throws Exception {

        ClienteDto responseDto = ClienteDto.builder()
                .id(1L)
                .identificacion("123456")
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .direccion("Calle 123")
                .telefono("123456789")
                .contrasena("password")
                .estado(true)
                .build();

        when(clienteService.save(any(ClienteDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"identificacion\":\"123456\", \"nombre\":\"Juan\", \"genero\":\"M\", \"edad\":30, \"direccion\":\"Calle 123\", \"telefono\":\"123456789\", \"contrasena\":\"password\", \"estado\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Almacenado Exitosamente"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.identificacion").value("123456"))
                .andExpect(jsonPath("$.data.nombre").value("Juan"))
                .andExpect(jsonPath("$.data.genero").value("M"))
                .andExpect(jsonPath("$.data.edad").value(30))
                .andExpect(jsonPath("$.data.direccion").value("Calle 123"))
                .andExpect(jsonPath("$.data.telefono").value("123456789"))
                .andExpect(jsonPath("$.data.contrasena").value("password"))
                .andExpect(jsonPath("$.data.estado").value(true));
    }

    @Test
    public void testGetAll() throws Exception {
        List<ClienteDto> clienteList = Arrays.asList(
                ClienteDto.builder()
                        .id(1L)
                        .identificacion("123456")
                        .nombre("Juan")
                        .genero("M").edad(30)
                        .direccion("Calle 123")
                        .telefono("123456789")
                        .contrasena("password")
                        .estado(true)
                        .build(),

                ClienteDto.builder()
                        .id(2L)
                        .identificacion("654321")
                        .nombre("Maria")
                        .genero("F")
                        .edad(25)
                        .direccion("Calle 456")
                        .telefono("987654321")
                        .contrasena("password")
                        .estado(true)
                        .build()
        );
        when(clienteService.getAll()).thenReturn(clienteList);

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].identificacion").value("123456"))
                .andExpect(jsonPath("$.data[0].nombre").value("Juan"))
                .andExpect(jsonPath("$.data[0].genero").value("M"))
                .andExpect(jsonPath("$.data[0].edad").value(30))
                .andExpect(jsonPath("$.data[0].direccion").value("Calle 123"))
                .andExpect(jsonPath("$.data[0].telefono").value("123456789"))
                .andExpect(jsonPath("$.data[0].contrasena").value("password"))
                .andExpect(jsonPath("$.data[0].estado").value(true))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].identificacion").value("654321"))
                .andExpect(jsonPath("$.data[1].nombre").value("Maria"))
                .andExpect(jsonPath("$.data[1].genero").value("F"))
                .andExpect(jsonPath("$.data[1].edad").value(25))
                .andExpect(jsonPath("$.data[1].direccion").value("Calle 456"))
                .andExpect(jsonPath("$.data[1].telefono").value("987654321"))
                .andExpect(jsonPath("$.data[1].contrasena").value("password"))
                .andExpect(jsonPath("$.data[1].estado").value(true));
    }

    @Test
    public void testGetById() throws Exception {
        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .identificacion("123456")
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .direccion("Calle 123")
                .telefono("123456789")
                .contrasena("password")
                .estado(true)
                .build();

        when(clienteService.getById(anyLong())).thenReturn(clienteDto);

        mockMvc.perform(get("/api/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.identificacion").value("123456"))
                .andExpect(jsonPath("$.data.nombre").value("Juan"))
                .andExpect(jsonPath("$.data.genero").value("M"))
                .andExpect(jsonPath("$.data.edad").value(30))
                .andExpect(jsonPath("$.data.direccion").value("Calle 123"))
                .andExpect(jsonPath("$.data.telefono").value("123456789"))
                .andExpect(jsonPath("$.data.contrasena").value("password"))
                .andExpect(jsonPath("$.data.estado").value(true));
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/api/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado Exitosamente"));
    }

    @Test
    public void testUpdate() throws Exception {
        ClienteDto requestDto = ClienteDto.builder()
                .id(1L)
                .identificacion("123456")
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .direccion("Calle 123")
                .telefono("123456789")
                .contrasena("password")
                .estado(true)
                .build();

        ClienteDto responseDto = ClienteDto.builder()
                .id(1L)
                .identificacion("123456")
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .direccion("Calle 123")
                .telefono("123456789")
                .contrasena("password")
                .estado(true)
                .build();

        when(clienteService.update(any(ClienteDto.class), anyLong())).thenReturn(responseDto);

        mockMvc.perform(put("/api/cliente/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"identificacion\":\"123456\", \"nombre\":\"Juan\", \"genero\":\"M\", \"edad\":30, \"direccion\":\"Calle 123\", \"telefono\":\"123456789\", \"contrasena\":\"password\", \"estado\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exito"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.identificacion").value("123456"))
                .andExpect(jsonPath("$.data.nombre").value("Juan"))
                .andExpect(jsonPath("$.data.genero").value("M"))
                .andExpect(jsonPath("$.data.edad").value(30))
                .andExpect(jsonPath("$.data.direccion").value("Calle 123"))
                .andExpect(jsonPath("$.data.telefono").value("123456789"));
    }
}