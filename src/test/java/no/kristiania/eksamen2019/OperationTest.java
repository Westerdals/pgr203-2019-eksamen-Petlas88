package no.kristiania.eksamen2019;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import java.util.Random;

public class OperationTest {

    private JdbcDataSource dataSource;

    public static Operation sampleOperation() {
        Operation operation = new Operation();
        operation.setName(pickOne(new String[]{"Battle of Hoth",
                "Battle of Endor", "Defence of the Death Star"}));
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

}
