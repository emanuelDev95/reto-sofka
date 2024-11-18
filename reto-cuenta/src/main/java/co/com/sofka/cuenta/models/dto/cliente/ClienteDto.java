package co.com.sofka.cuenta.models.dto.cliente;


public record ClienteDto(Long id,
                         String identificacion,
                         String nombre, String genero,
                         Integer edad,
                         String direccion,
                         String telefono,
                         String contrasena,
                         Boolean estado) {


}
