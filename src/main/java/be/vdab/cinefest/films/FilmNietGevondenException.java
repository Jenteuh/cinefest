package be.vdab.cinefest.films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class FilmNietGevondenException extends RuntimeException {
    public FilmNietGevondenException(long id) {
        super("Film niet gevonden: " + id);
    }
}
