package co.com.sofka.persona.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor
public class Persona extends AbstractEntity<Long> implements Serializable {

    private String identificacion;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;



    public Persona(Long id, String identificacion, String nombre, String genero, Integer edad, String direccion, String telefono) {
        super(id);
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }


}



