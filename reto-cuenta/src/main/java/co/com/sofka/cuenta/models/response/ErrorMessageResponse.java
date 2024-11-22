package co.com.sofka.cuenta.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorMessageResponse(Integer value,
                                   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                   Date date,
                                   String message) {

}
