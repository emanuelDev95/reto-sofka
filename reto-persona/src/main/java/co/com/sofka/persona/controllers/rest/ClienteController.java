package co.com.sofka.persona.controllers.rest;

import co.com.sofka.persona.models.dto.ClienteCuentaDto;
import co.com.sofka.persona.models.dto.ClienteDto;
import co.com.sofka.persona.models.response.MessageResponse;
import co.com.sofka.persona.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping()
    public ResponseEntity<MessageResponse<ClienteDto>> save(@RequestBody ClienteDto entity) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(
                        "Almacenado Exitosamente",
                        HttpStatus.CREATED.value(),
                        this.clienteService.save(entity)));
    }

    @GetMapping()
    public ResponseEntity<MessageResponse<List<ClienteDto>>> getAll() {
        return ResponseEntity.ok(new MessageResponse<>(
                "OK", HttpStatus.OK.value(),
                this.clienteService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse<ClienteDto>> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<ClienteDto>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.clienteService.getById(id))
                .build());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<Void>> deleteById(@PathVariable @NonNull Long id){
        this.clienteService.delete(id);
        return ResponseEntity.ok(MessageResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Eliminado Exitosamente")
                .build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<ClienteDto>> update(@RequestBody ClienteDto productDTO,
                                                           @PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<ClienteDto>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.clienteService.update(productDTO,id))
                .build());


    }

    @PostMapping("/client-account")
    public ResponseEntity<MessageResponse<ClienteDto>> saveWithAccount(@RequestBody ClienteCuentaDto entity) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(
                        "Almacenado Exitosamente",
                        HttpStatus.CREATED.value(),
                        this.clienteService.saveClienteCuenta(entity)));
    }
}
