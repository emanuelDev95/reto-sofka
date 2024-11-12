package co.com.sofka.persona.persistence.repository;

import co.com.sofka.persona.persistence.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
