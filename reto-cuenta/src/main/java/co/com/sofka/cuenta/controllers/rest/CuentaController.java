package co.com.sofka.cuenta.controllers.rest;

import co.com.sofka.cuenta.models.dto.cuenta.CuentaDto;
import co.com.sofka.cuenta.models.dto.cuenta.CuentaRequestDto;
import co.com.sofka.cuenta.models.response.MessageResponse;
import co.com.sofka.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping()
    public ResponseEntity<MessageResponse<CuentaDto>> save(@RequestBody CuentaRequestDto entity) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(
                        "Almacenado Exitosamente",
                        HttpStatus.CREATED.value(),
                        this.cuentaService.save(entity)));
    }

    @GetMapping()
    public ResponseEntity<MessageResponse<List<CuentaDto>>> getAll() {
        return ResponseEntity.ok(new MessageResponse<>(
                "OK", HttpStatus.OK.value(),
                this.cuentaService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse<CuentaDto>> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<CuentaDto>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.cuentaService.getById(id))
                .build());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<Void>> deleteById(@PathVariable @NonNull Long id){
        this.cuentaService.delete(id);
        return ResponseEntity.ok(MessageResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Eliminado Exitosamente")
                .build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<CuentaDto>> update(@RequestBody CuentaRequestDto dto,
                                                              @PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<CuentaDto>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.cuentaService.update(dto,id))
                .build());


    }
}
