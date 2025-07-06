package be.vdab.cinefest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
public class FilmsControllerTest {

    private final static String FILMS_TABLE = "films";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    FilmsControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findTotaalVrijePlaatsenVindtJuisteAantalVrijePlaatsen() {
        var vrijePlaatsen = jdbcClient.sql(
                "select sum(vrijePlaatsen) from films")
                .query(Integer.class)
                .single();
        var response = mockMvcTester.get()
                .uri("/films/totaalvrijeplaatsen");
        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$")
                .isEqualTo(vrijePlaatsen);
    }

}
