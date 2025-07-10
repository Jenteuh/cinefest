package be.vdab.cinefest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql({"/films.sql","/reservaties.sql"})
@AutoConfigureMockMvc
public class ReservatieControllerTest {

    private final static String RESERVATIES_TABLE = "reservaties";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public ReservatieControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByEmailAdresVindtDeJuisteReservatie() {

        var aantalReservaties = JdbcTestUtils.countRowsInTableWhere(jdbcClient,
                RESERVATIES_TABLE, "emailAdres = 'test@example.org'");
        var response = mockMvcTester.get()
                .uri("/reservaties?emailAdres={emailAdres}", "test@example.org");
        assertThat(response).hasStatusOk()
                .bodyJson().extractingPath("length()").isEqualTo(aantalReservaties);

    }

}
