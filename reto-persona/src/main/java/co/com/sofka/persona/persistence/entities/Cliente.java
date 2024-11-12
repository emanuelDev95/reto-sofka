package co.com.sofka.persona.persistence.entities;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Entity(name = "clientes")
@PrimaryKeyJoinColumn(referencedColumnName = "id")

@Getter @Setter @NoArgsConstructor
public class Cliente extends Persona  implements Serializable {

   @UuidGenerator
   @Column(unique = true, updatable = false)
    private String clienteId;
    private String contrasena;
    private Boolean estado;

    @Builder
    public Cliente(Long idPersona, String identificacion, String nombre, String genero, Integer edad, String direccion,
                   String telefono, String clienteId, String contrasena, Boolean estado) {
        super(idPersona, identificacion, nombre, genero, edad, direccion, telefono);
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

}
