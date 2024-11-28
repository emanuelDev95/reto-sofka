package co.com.sofka.persona.controllers.handler;

import co.com.sofka.persona.exceptions.ResourceNotFoundException;
import co.com.sofka.persona.models.response.ErrorMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

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
    public void testGlobalExceptionHandler() {
        Exception ex = new Exception("Internal server error");
        ResponseEntity<ErrorMessageResponse> response = controllerExceptionHandler.globalExceptionHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().value());
        assertEquals("Internal server error", response.getBody().message());
    }
}
