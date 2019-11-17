package no.kristiania.eksamen2019.DAO;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectiveTest {
    private JdbcDataSource dataSource;

    public static Objective sampleObjective() {
        Objective objective = new Objective();
        objective.setName(pickOne(new String[]{"Find Luke Skywalker",
                "Destroy the Rebel Fleet", "Make tea"}));
        objective.setDescription("Should be pretty self-explanatory...");
        objective.setStatus("To do for the glory of the Empire");
        return objective;
    }

    private static String pickOne(String[] alternatives) {
        Random random = new Random();

        return alternatives[random.nextInt(alternatives.length)];
    }

    @BeforeEach
    void testDataSource() {
        dataSource = createDataSource();
        Flyway.configure().dataSource(dataSource).load().migrate();
    }

    private JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL( "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1" );
        Flyway.configure().dataSource( dataSource ).load().migrate();
        return dataSource;
    }

    @Test
    void shouldFindObjectiveInDb() throws SQLException {
        Objective objective = sampleObjective();
        ObjectiveDao dao = new ObjectiveDao(dataSource);

        dao.insert(objective);
        System.out.println(dao.listAll());
        assertThat(dao.listAll()).contains(objective);
    }

}
