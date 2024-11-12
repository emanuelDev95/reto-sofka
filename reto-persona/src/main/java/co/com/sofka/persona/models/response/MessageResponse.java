package co.com.sofka.persona.models.response;
import lombok.Builder;

@Builder
public record MessageResponse<D>
        (String message,
         Integer statusCode,
         D data) {
}
