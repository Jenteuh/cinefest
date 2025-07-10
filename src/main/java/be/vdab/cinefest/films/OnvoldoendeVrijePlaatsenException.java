package be.vdab.cinefest.films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OnvoldoendeVrijePlaatsenException extends RuntimeException {
    OnvoldoendeVrijePlaatsenException() {
        super("Onvoldoende vrije plaatsen");
    }
}