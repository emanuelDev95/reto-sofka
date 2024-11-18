package co.com.sofka.cuenta.models.response;
import lombok.Builder;

@Builder
public record MessageResponse<D>
        (String message,
         Integer statusCode,
         D data) {
}
