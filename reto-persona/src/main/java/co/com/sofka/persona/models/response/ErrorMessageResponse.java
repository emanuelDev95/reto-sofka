package co.com.sofka.persona.models.response;

import java.util.Date;

public record ErrorMessageResponse(Integer value,
                                   Date date,
                                   String message) {

}
