package no.kristiania.eksamen2019.DAO;

import no.kristiania.eksamen2019.Operation;
import no.kristiania.eksamen2019.OperationDao;
import no.kristiania.eksamen2019.Operation;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {
/* This class is not used right now, but tests might be used if it's implemented later
    private JdbcDataSource dataSource;

    public static Operation sampleOperation() {
        Operation operation = new Operation();
        operation.setName(pickOne(new String[]{"Maintenance of the Death Star",
                "Upgrade Darth Vader's Suit", "Secure sufficient tea exports"}));
        return operation;
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
    void shouldFindOperationInDb() throws SQLException {
        Operation operation = sampleOperation();
        OperationDao dao = new OperationDao(dataSource);

        dao.insert(operation);
        System.out.println(dao.listAll());
        assertThat(dao.listAll()).contains(operation);
    }
*/
}
