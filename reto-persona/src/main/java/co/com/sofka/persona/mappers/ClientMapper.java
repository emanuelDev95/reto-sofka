package co.com.sofka.persona.mappers;

import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.persistence.entities.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements GenericMapper<ClienteDto, Cliente, Long> {
    @Override
    public Cliente toEntity(ClienteDto dto) {
        return Cliente.builder()
                .idPersona(dto.id())
                .estado(dto.estado())
                .contrasena(dto.contrasena())
                .identificacion(dto.identificacion())
                .nombre(dto.nombre())
                .genero(dto.genero())
                .direccion(dto.direccion())
                .telefono(dto.telefono())
                .edad(dto.edad())
                .build();


    }

    @Override
    public ClienteDto toDto(Cliente entity) {
        return ClienteDto.builder()
                .id(entity.getId())
                .estado(entity.getEstado())
                .contrasena(entity.getContrasena())
                .identificacion(entity.getIdentificacion())
                .nombre(entity.getNombre())
                .genero(entity.getGenero())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .edad(entity.getEdad())
                .build();
    }
}
