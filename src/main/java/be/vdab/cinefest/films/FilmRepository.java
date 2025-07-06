package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FilmRepository {

    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    int findAantalVrijePlaatsen() {
        var sql = """
                SELECT SUM(vrijePlaatsen) AS totaalVrijePlaatsen
                FROM films;
                """;
        return jdbcClient.sql(sql)
                .query(Integer.class)
                .single();
    }

    Optional<Film> findById(long id) {
        var sql = """
                select id, titel, jaar, vrijeplaatsen, aankoopprijs
                from films
                where id = ?;
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }

}
