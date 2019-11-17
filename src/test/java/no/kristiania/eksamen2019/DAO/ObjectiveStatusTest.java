package no.kristiania.eksamen2019.DAO;

import no.kristiania.eksamen2019.ObjectiveStatus;
import no.kristiania.eksamen2019.ObjectiveStatusDao;
import org.assertj.core.api.AssertionsForClassTypes;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ObjectiveStatusTest {

    private JdbcDataSource dataSource;

    public static ObjectiveStatus sampleObjectiveStatus() {
        ObjectiveStatus objectiveStatus = new ObjectiveStatus();
        objectiveStatus.setName(pickOne(new String[]{"To do for the glory of the Empire",
                "In Progress for the glory of the Empire", "Completed for the glory of the Empire"}));
        return objectiveStatus;
    }

    private static String pickOne(String[] alternatives){
        Random random = new Random();
        return alternatives[random.nextInt(alternatives.length)];
    }

    @BeforeEach
    void testDataSource() {
        dataSource = createDataSource();
        Flyway.configure().dataSource(dataSource).load().migrate();
    }

    @AfterEach
    void restartDataSource() {
        Flyway.configure().dataSource(dataSource).load().clean();
        Flyway.configure().dataSource(dataSource).load().migrate();
    }

    static JdbcDataSource createDataSource(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldFindObjectiveStatusesinDB() throws SQLException {
        ObjectiveStatus objectiveStatus = sampleObjectiveStatus();
        ObjectiveStatusDao dao = new ObjectiveStatusDao(dataSource);

        dao.insert(objectiveStatus);
        System.out.println(dao.listAll());
        assertThat(dao.listAll()).contains(objectiveStatus);
    }

    @Test
    void shouldFindGeneratedIdInDb() throws SQLException {
        ObjectiveStatusDao dao = new ObjectiveStatusDao(dataSource);
        ObjectiveStatus objectiveStatus = new ObjectiveStatus();
        long id = dao.insert(objectiveStatus);
        AssertionsForClassTypes.assertThat(dao.retrieve(id)).isEqualToComparingFieldByField(objectiveStatus);
    }
}
