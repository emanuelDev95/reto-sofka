package co.com.sofka.cuenta.service;



import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;

import java.util.List;

public interface MovimientoService  {

    MovimientoDTO save(MovimientoRequestDTO dto);
    List<MovimientoDTO> getAll();
    MovimientoDTO getById(Long id);
    void delete( Long id);
    MovimientoDTO update(MovimientoRequestDTO dto, Long id);

}
