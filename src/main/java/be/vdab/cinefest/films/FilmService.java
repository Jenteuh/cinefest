package be.vdab.cinefest.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    int findAantalVrijePlaatsen() {
        return filmRepository.findAantalVrijePlaatsen();
    }

    Optional<Film> findById(long id) {
        return filmRepository.findById(id);
    }

}
