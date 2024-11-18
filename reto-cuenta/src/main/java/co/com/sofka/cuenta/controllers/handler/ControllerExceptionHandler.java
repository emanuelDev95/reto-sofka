package co.com.sofka.cuenta.controllers.handler;

import co.com.sofka.cuenta.exceptions.BadRequestException;
import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.models.response.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    public ControllerExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageResponse resourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorMessageResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse> badRequestExceptionHandler(BadRequestException ex) {
        var response = new ErrorMessageResponse(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> globalExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        new Date(),
                        ex.getMessage()));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorMessageResponse> restClientExceptionHandler(RestClientException ex) {
        String jsonString = ex.getMessage(); // Obtener el mensaje de la excepción

        try {
            // Extraer la parte JSON anidada
            String jsonContent = jsonString.substring(jsonString.indexOf('{'), jsonString.lastIndexOf('}') + 1);

            // Convertir JSON a ErrorMessageResponse
            ErrorMessageResponse response = objectMapper.readValue(jsonContent, ErrorMessageResponse.class);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Si hay un error, devolver un mensaje genérico
            ErrorMessageResponse response = ErrorMessageResponse.builder()
                    .value(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .date(new Date())
                    .message("Error processing request")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
