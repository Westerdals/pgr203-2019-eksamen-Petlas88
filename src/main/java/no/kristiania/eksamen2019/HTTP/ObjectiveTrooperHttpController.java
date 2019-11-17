package no.kristiania.eksamen2019.HTTP;


import no.kristiania.eksamen2019.DAO.*;
import no.kristiania.eksamen2019.Server.HttpServer;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ObjectiveTrooperHttpController implements HttpController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(TrooperHttpController.class);
    private ObjectiveDao objectiveDao;
    private TrooperDao trooperDao;
    private ObjectiveTrooperDao objectiveTrooperDao;


    public ObjectiveTrooperHttpController(ObjectiveTrooperDao objectiveTrooperDao,
                                          TrooperDao trooperDao, ObjectiveDao objectiveDao) {
        this.trooperDao = trooperDao;
        this.objectiveTrooperDao = objectiveTrooperDao;
        this.objectiveDao = objectiveDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String,
            String> requestParameters, String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {
                requestParameters = HttpServer.parseRequestParameters(requestBody);
                ObjectiveTrooper objectiveTrooper = new ObjectiveTrooper();
                objectiveTrooper.setTrooperId(Integer.parseInt(requestParameters.get("trooperName")));
                objectiveTrooper.setObjectiveId(Integer.parseInt(requestParameters.get("objectiveName")));

                objectiveTrooperDao.insert(objectiveTrooper);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n"+
                        "Location: http://localhost:8080/\r\n"+
                        "Connection:close\r\n"+
                        "\r\n").getBytes());


                return;

            }

            String statusCode = requestParameters.getOrDefault("status", "200");
            String location = requestParameters.get("location");
            String body = requestParameters.getOrDefault("body", getBody());

            outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +

                    "Content-length: " + body.length() + "\r\n" +
                    (location != null ? "Location: " + location + "\r\n" : "") +
                    "\r\n" +
                    body).getBytes());
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

        List<Objective> objectives = objectiveDao.listAll();
        for (int i = 0; i < objectives.size(); i++) {

            String objectiveId = "" + objectives.get(i).getId();
            String sql = "select m.* from objectivetroopers tm " +
                    "join troopers m on tm.member_id = m.id where tm.objective_id = " + objectiveId;
            String memberName = "";
            System.out.println(i);

            List<Trooper> troopers = trooperDao.listAll(sql);
            System.out.println("size: " + troopers.size());
            for (int j = 0; j < troopers.size(); j++) {
                memberName = memberName + troopers.get(j).getName() + ", ";

            }
            String status = objectives.get(i).getStatus();
            if(status == null){
                status = "No status";
            }

            bod.append("<article>\n" +
                    "        <h1>" + objectives.get(i).getName() + "</h1>\n" +
                    "\n" +
                    "        <h4> Status: </h4>\n" +
                    "\n" +
                    "        <p>" + status + "</p>\n" +
                    "\n" +
                    "        <h4> Members: </h4>\n" +
                    "\n" +
                    "        <div>" + memberName + "</div>\n" +
                    "\n" +
                    "    </article>");
        }
        body = bod.toString();


        return body;
    }


}
