package co.com.sofka.cuenta.service.impl;

import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.mappers.CuentaMapper;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.persistence.entities.Cuenta;
import co.com.sofka.cuenta.persistence.repositories.CuentaRepository;
import co.com.sofka.cuenta.service.ClienteService;
import co.com.sofka.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static co.com.sofka.cuenta.constants.CuentaConstant.CUENTA_NO_ENCONTRADA;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {


    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;
    private final CuentaMapper cuentaMapper;


    @Override
    public CuentaDto save(CuentaRequestDto cuentaRequestDto) {
        var cliente = clienteService.getClienteById(cuentaRequestDto.clienteId());
        var cuenta = cuentaRepository.saveAndFlush(cuentaMapper.cuentaToCuentaRequestDto(cuentaRequestDto));
        return cuentaMapper.cuentaToCuentaDto(cuenta, cliente);
    }



    @Override
    public List<CuentaDto> getAll() {
        var cuentas = cuentaRepository.findAll();

        var cuentasMap = cuentas
                .stream()
                .collect(Collectors.groupingBy(Cuenta::getClienteId));

        var clientes = cuentas.stream()
                .map(Cuenta::getClienteId)
                .distinct()
                .map(clienteService::getClienteById)
                .toList();

        return clientes.stream()
                .flatMap(clienteDto -> cuentasMap.get(clienteDto.id()).stream()
                        .map(cuenta -> cuentaMapper.cuentaToCuentaDto(cuenta, clienteDto)))
                .toList();
    }


    @Override
    public CuentaDto getById(Long id) {
        var cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException(CUENTA_NO_ENCONTRADA));

        var cliente = clienteService.getClienteById(cuenta.getClienteId());

        return cuentaMapper.cuentaToCuentaDto(cuenta, cliente);
    }

    @Override
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Override
    public CuentaDto update(CuentaRequestDto dto, Long id) {
        var cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException(CUENTA_NO_ENCONTRADA));

        cuenta = cuentaMapper.cuentaToCuentaRequestDto(dto);

        cuenta.setId(id);

        cuentaRepository.saveAndFlush(cuenta);
        var cliente = clienteService.getClienteById(cuenta.getClienteId());
        return cuentaMapper.cuentaToCuentaDto(cuenta, cliente);
    }
}
