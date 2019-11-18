package no.kristiania.eksamen2019.HTTP;

import no.kristiania.eksamen2019.DAO.Objective;
import no.kristiania.eksamen2019.DAO.ObjectiveDao;
import no.kristiania.eksamen2019.DAO.Trooper;
import no.kristiania.eksamen2019.DAO.TrooperDao;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ListObjectiveTroopersHttpController implements HttpController {
    private ObjectiveDao objectiveDao;
    private TrooperDao trooperDao;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ListObjectiveTroopersHttpController.class);

    public ListObjectiveTroopersHttpController(ObjectiveDao objectiveDao, TrooperDao trooperDao) {
        this.objectiveDao = objectiveDao;
        this.trooperDao = trooperDao;
    }


    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters, String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {


                return;

            }

            String statusCode = requestParameters.getOrDefault("status", "200");
            String location = requestParameters.get("location");
            String body = requestParameters.getOrDefault("body", getBody());

            outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                    "Location: http://localhost:8080/ListObjectiveTroopers.html\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
            return;

        } catch (SQLException e) {
            Logger.error("While handling request{}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.0 500 Internal server error\r\n" +
                    "Content-length: " + message.length() + "\r\n" +
                    "\r\n" +
                    message).getBytes());
        }

    }


    public String getBody() throws SQLException {

        String body = "";
        StringBuilder bod = new StringBuilder();

        List<Trooper> trooper = trooperDao.listAll();
        for (int i = 0; i < trooper.size(); i++) {

            String trooperId = "" + trooper.get(i).getId();
            String sql = "select t.*, null n from objectivetroopers tm join objectives t on tm.objective_id = t.id where tm.trooper_id = " + trooperId;
            String objectiveNames = "";

            List<Objective> objectives = objectiveDao.listAll(sql);

            for (int j = 0; j < objectives.size(); j++) {
                objectiveNames = objectiveNames + objectives.get(j).getName() + ", ";
            }
            System.out.println("i: " + i);
            bod.append("<article>\n" +
                    "<h4>Member: </h4>" +
                    "\n" +
                    "        <h4> " + trooper.get(i).getName() + "</h4>\n" +
                    "\n" +

                    "\n" +
                    "<h4>Objectives:</h4>" +
                    "\n" +
                    "        <div>" + objectiveNames + "</div>\n" +
                    "\n" +
                    "    </article>");
        }
        body = bod.toString();


        return body;
    }

}