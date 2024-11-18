package co.com.sofka.cuenta.persistence.repositories;

import co.com.sofka.cuenta.persistence.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

   <T> List<T> findByCuentaClienteIdAndFechaBetween(Long CuentaClienteId,
                                                    LocalDateTime fechaInicio,
                                                    LocalDateTime fechaFin,
                                                    Class<T> entityClass);
}

