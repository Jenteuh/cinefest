package be.vdab.cinefest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
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

    @Test
    void findByIdMetEenOnbestaandeIdGeeftNotFound() {
        var response = mockMvcTester.get()
                .uri("/films/{id}", Long.MAX_VALUE);
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }

    private int idVanTest1Film() {
        return jdbcClient.sql("select id from films where titel = 'test1'")
                .query(Integer.class)
                .single();
    }

    @Test
    void findByIdMetEenBestaandeIdVindtDeFilm() {
        var id = idVanTest1Film();
        var response = mockMvcTester.get()
                .uri("/films/{id}", id);
        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .satisfies(
                        json -> assertThat(json).extractingPath("id").isEqualTo(id),
                        json -> assertThat(json).extractingPath("titel").isEqualTo("test1"));
    }

    @Test
    void findAllVindtAlleFilms() {
        var response = mockMvcTester.get().uri("/films");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, FILMS_TABLE));
    }

    @Test
    void findByJaarVindtDeJuisteFilms() {
        var response = mockMvcTester.get()
                .uri("/films")
                .queryParam("jaar", "1970");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,  "jaar = 1970"));
    }

    @Test
    void deleteVerwijdertDeFilm(){
        var id = idVanTest1Film();
        var response = mockMvcTester.delete()
                .uri("/films/{id}", id);
        assertThat(response).hasStatusOk();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,
                "id = " + id)).isZero();
    }

}
