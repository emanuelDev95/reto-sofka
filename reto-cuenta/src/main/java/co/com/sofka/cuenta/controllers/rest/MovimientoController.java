package co.com.sofka.cuenta.controllers.rest;

import co.com.sofka.cuenta.models.dto.movimiento.MovimientoDTO;
import co.com.sofka.cuenta.models.dto.movimiento.MovimientoRequestDTO;
import co.com.sofka.cuenta.models.dto.movimiento.ReporteMovimientoDTO;
import co.com.sofka.cuenta.models.response.MessageResponse;
import co.com.sofka.cuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;


    @PostMapping
    ResponseEntity<MessageResponse<MovimientoDTO>> registrarMovimiento(@RequestBody MovimientoRequestDTO movimientoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(
                        "Almacenado Exitosamente",
                        HttpStatus.CREATED.value(),
                        this.movimientoService.save(movimientoDTO)));
    }

    @GetMapping()
    public ResponseEntity<MessageResponse<List<MovimientoDTO>>> getAll() {
        return ResponseEntity.ok(new MessageResponse<>(
                "OK", HttpStatus.OK.value(),
                this.movimientoService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse<MovimientoDTO>> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<MovimientoDTO>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.movimientoService.getById(id))
                .build());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<Void>> deleteById(@PathVariable @NonNull Long id) {
        this.movimientoService.delete(id);
        return ResponseEntity.ok(MessageResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Eliminado Exitosamente")
                .build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<MovimientoDTO>> update(@RequestBody MovimientoRequestDTO dto,
                                                                 @PathVariable @NonNull Long id) {
        return ResponseEntity.ok(MessageResponse.<MovimientoDTO>builder()
                .message("Exito")
                .statusCode(HttpStatus.OK.value())
                .data(this.movimientoService.update(dto, id))
                .build());


    }

    @GetMapping("/reporte")
    public ResponseEntity<MessageResponse<List<ReporteMovimientoDTO>>> getReporte(@RequestParam("startDate")
                                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                                  @RequestParam("endDate")
                                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                                  @RequestParam Long cliente) {
        List<ReporteMovimientoDTO> reportes = movimientoService.getReportByDateAndCustomer(startDate, endDate, cliente);
        return ResponseEntity.ok(new MessageResponse<>("OK", HttpStatus.OK.value(), reportes));
    }


}
