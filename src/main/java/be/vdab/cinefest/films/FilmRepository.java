package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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

}
