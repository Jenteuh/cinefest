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
@Sql("/medewerkers.sql")
@AutoConfigureMockMvc
public class MedewerkerControllerTest {

    private final static String MEDEWERKERS_TABLE = "medewerkers";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public MedewerkerControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByStukVoornaamEnStukFamilienaamVindtDeJuisteMedewerkers() {
        var response = mockMvcTester.get().uri("/medewerkers")
                .queryParam("stukVoornaam", "testvoornaam1")
                .queryParam("stukFamilienaam", "testfamilienaam1");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MEDEWERKERS_TABLE,
                        """
                                voornaam like '%testvoornaam1%' and
                                familienaam like '%testfamilienaam1%'
                                """));
    }
}
