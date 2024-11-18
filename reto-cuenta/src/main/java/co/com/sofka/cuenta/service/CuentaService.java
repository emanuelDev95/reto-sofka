package co.com.sofka.cuenta.service;

import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;

import java.util.List;

public interface CuentaService {


    CuentaDto save(CuentaRequestDto cuentaRequestDto);
    List<CuentaDto> getAll();
    CuentaDto getById(Long id);
    void delete( Long id);
    CuentaDto update(CuentaRequestDto dto, Long id);
}
