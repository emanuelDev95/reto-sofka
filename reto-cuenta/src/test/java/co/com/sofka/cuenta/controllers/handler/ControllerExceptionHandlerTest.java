package co.com.sofka.cuenta.controllers.handler;

import co.com.sofka.cuenta.exceptions.BadRequestException;
import co.com.sofka.cuenta.exceptions.ResourceNotFoundException;
import co.com.sofka.cuenta.models.response.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;


    @Test
    public void testResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ErrorMessageResponse response = controllerExceptionHandler.resourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.value());
        assertEquals("Resource not found", response.message());
    }

    @Test
    public void testBadRequestExceptionHandler() {
        BadRequestException ex = new BadRequestException("Bad request");
        ResponseEntity<ErrorMessageResponse> response = controllerExceptionHandler.badRequestExceptionHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().value());
        assertEquals("Bad request", response.getBody().message());
    }

    @Test
    public void testGlobalExceptionHandler() {
        Exception ex = new Exception("Internal server error");
        ResponseEntity<ErrorMessageResponse> response = controllerExceptionHandler.globalExceptionHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().value());
        assertEquals("Internal server error", response.getBody().message());
    }

    @Test
    public void testRestClientExceptionHandler() throws Exception {
        RestClientException ex = new RestClientException("{\"value\":500,\"date\":\"2024-11-18T18:27:32.095+00:00\",\"message\":\"Client error\"}");
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .value(500)
                .date(new Date())
                .message("Client error")
                .build();

        when(objectMapper.readValue("{\"value\":500,\"date\":\"2024-11-18T18:27:32.095+00:00\",\"message\":\"Client error\"}", ErrorMessageResponse.class))
                .thenReturn(errorMessageResponse);

        ResponseEntity<ErrorMessageResponse> response = controllerExceptionHandler.restClientExceptionHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().value());
        assertEquals("Client error", response.getBody().message());
    }
}
