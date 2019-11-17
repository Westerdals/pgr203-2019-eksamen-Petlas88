package no.kristiania.eksamen2019;

import org.assertj.core.api.AssertionsForClassTypes;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TrooperTest {

    private JdbcDataSource dataSource;

    public static Trooper sampleTrooper() {
        Trooper trooper = new Trooper();
        trooper.setName(pickOne(new String[]{"Darth Vader", "Jar-Jar Binks", "General Grievous"}));
        trooper.setEmail("testing_if@email.shows");
        return trooper;
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
    void shouldFindtrooperinDB() throws SQLException {
        Trooper trooper = sampleTrooper();
        TrooperDao dao = new TrooperDao(dataSource);

        dao.insert(trooper);
        System.out.println(dao.listAll());
        assertThat(dao.listAll()).contains(trooper);
    }

    @Test
    void shouldSaveFromInsert() throws SQLException {
        TrooperDao dao = new TrooperDao(dataSource);
        Trooper trooper = new Trooper();
        long id = dao.insert(trooper);
        AssertionsForClassTypes.assertThat(dao.retrieve(id)).isEqualToComparingFieldByField(trooper);
    }
}
