package co.com.sofka.persona.models.dto;

import lombok.Builder;


@Builder
public record ClienteDto(Long id,
                         String identificacion,
                         String nombre, String genero,
                         Integer edad,
                         String direccion,
                         String telefono,
                         String contrasena,
                         Boolean estado) {


}
