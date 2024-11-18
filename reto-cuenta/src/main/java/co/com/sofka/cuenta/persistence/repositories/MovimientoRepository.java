package co.com.sofka.cuenta.persistence.repositories;

import co.com.sofka.cuenta.persistence.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}

