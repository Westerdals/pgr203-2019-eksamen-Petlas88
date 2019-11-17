package no.kristiania.eksamen2019.Server;

import no.kristiania.eksamen2019.DAO.ObjectiveDao;
import no.kristiania.eksamen2019.DAO.ObjectiveStatusDao;
import no.kristiania.eksamen2019.DAO.ObjectiveTrooperDao;
import no.kristiania.eksamen2019.DAO.TrooperDao;
import no.kristiania.eksamen2019.HTTP.*;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ManagerServer {

    public static void main(String[] args) throws IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        Properties properties = new Properties();
        try(FileReader reader = new FileReader("task-manager.properties")){
            properties.load(reader);
        }
        dataSource. setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();


        HttpServer server = new HttpServer(8080);
        server.setFileLocation("src/main/resources/website");
        server.addController("/api/troopers", new TrooperHttpController(new TrooperDao(dataSource)));
        server.addController("/api/objectives", new ObjectiveHttpController(new ObjectiveDao(dataSource)));
        server.addController("/api/status", new ObjectiveStatusHttpController(new ObjectiveStatusDao(dataSource)));
        server.addController("/api/editStatus", new EditObjectiveStatusHttpController(new ObjectiveDao(dataSource)));
        server.addController("/api/addObjectiveTrooper", new ObjectiveTrooperHttpController(new ObjectiveTrooperDao(dataSource),
                new TrooperDao(dataSource), new ObjectiveDao(dataSource)));
        server.start();
    }
}

