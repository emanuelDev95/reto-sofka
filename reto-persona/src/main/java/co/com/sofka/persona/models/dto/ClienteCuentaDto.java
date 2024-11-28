package co.com.sofka.persona.models.dto;

import java.math.BigDecimal;

public record ClienteCuentaDto(
        String identificacion,
        String nombre,
        String genero,
        Integer edad,
        String direccion,
        String telefono,
        String contrasena,
        Boolean estado,
        String numeroCuenta,
        String tipo,
        BigDecimal saldo,
        Boolean estadoCuenta){
}
