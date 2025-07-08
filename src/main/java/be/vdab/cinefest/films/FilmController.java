package be.vdab.cinefest.films;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
public class FilmController {

    private final FilmService filmService;

    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private record IdTitelJaarVrijePlaatsen(long id, String titel, int jaar, int vrijePlaatsen) {
        IdTitelJaarVrijePlaatsen(Film film) {
            this(film.getId(), film.getTitel(),  film.getJaar(), film.getVrijePlaatsen());
        }
    }

    @GetMapping("films/totaalvrijeplaatsen")
    int findTotaalVrijePlaatsen() {
        return filmService.findAantalVrijePlaatsen();
    }

    @GetMapping("/films/{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(film -> new IdTitelJaarVrijePlaatsen(film))
                .orElseThrow(() -> new FilmNietGevondenException(id));
    }

    @GetMapping("films")
    Stream<IdTitelJaarVrijePlaatsen> findAll() {
        return filmService.findAll()
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @GetMapping(value = "films", params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar) {
        return filmService.findByJaar(jaar)
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @DeleteMapping("films/{id}")
    void delete(@PathVariable long id) {
        filmService.delete(id);
    }

    @PostMapping("films")
    long create(@RequestBody @Valid NieuweFilm nieuweFilm) {
        var id = filmService.create(nieuweFilm);
        return id;
    }

}
