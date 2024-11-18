package co.com.sofka.cuenta.models.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorMessageResponse(Integer value,
                                   Date date,
                                   String message) {

}
