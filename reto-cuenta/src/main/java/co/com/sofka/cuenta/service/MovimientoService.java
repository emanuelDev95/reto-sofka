package co.com.sofka.cuenta.service;



import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService  {

    MovimientoDTO save(MovimientoRequestDTO dto);
    List<MovimientoDTO> getAll();
    MovimientoDTO getById(Long id);
    void delete( Long id);
    MovimientoDTO update(MovimientoRequestDTO dto, Long id);
    List<ReporteMovimientoDTO> getReportByDateAndCustomer(LocalDateTime start, LocalDateTime end, Long customerId);

}
