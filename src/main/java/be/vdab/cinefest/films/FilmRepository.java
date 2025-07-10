package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    List<Film> findAll() {
        var sql = """
                select id, titel, jaar, vrijeplaatsen, aankoopprijs
                from films
                order by titel
                """;
        return jdbcClient.sql(sql)
                .query(Film.class)
                .list();
    }

    List<Film> findByJaar(int jaar) {
        var sql = """
                select id, titel, jaar, vrijeplaatsen, aankoopprijs
                from films
                where jaar = ?
                order by titel
                """;
        return jdbcClient.sql(sql)
                .param(jaar)
                .query(Film.class)
                .list();
    }

    void delete(long id) {
        var sql = """
                delete from films
                where id = ?;
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }

    long create(Film film) {
        var sql = """
                insert into films(titel, jaar, vrijeplaatsen, aankoopprijs)
                values(?, ?, ?, ?);
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(film.getTitel(), film.getJaar(), film.getVrijePlaatsen(), film.getAankoopprijs())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }

    void updateTitel(long id, String titel) {
        var sql = """
                update films
                set titel = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql).params(titel, id).update() == 0) {
            throw new FilmNietGevondenException(id);
        }
    }

    Optional<Film> findAndLockById(long id) {
        var sql = """
                select id, titel, jaar, vrijePlaatsen, aankoopprijs
                from films
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql).param(id).query(Film.class).optional();
    }

    void updateVrijePlaatsen(long id, int vrijePlaatsen) {
        var sql = """
                update films
                set vrijePlaatsen = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql).params(vrijePlaatsen, id).update() == 0) {
            throw new FilmNietGevondenException(id);
        }
    }

}
