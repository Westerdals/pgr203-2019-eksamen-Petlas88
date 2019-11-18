package no.kristiania.eksamen2019.HTTP;

import no.kristiania.eksamen2019.DAO.Objective;
import no.kristiania.eksamen2019.DAO.ObjectiveDao;
import no.kristiania.eksamen2019.Server.HttpServer;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class EditObjectiveStatusHttpController implements HttpController {
    private ObjectiveDao objectiveDao;
    private static final org.slf4j.Logger Logger =
            LoggerFactory.getLogger(ObjectiveHttpController.class);

    public EditObjectiveStatusHttpController(ObjectiveDao objectiveDao) {
        this.objectiveDao = objectiveDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String,
            String> requestParameters, String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {
                requestParameters = HttpServer.parseRequestParameters(requestBody);

                String statusName = requestParameters.get("statusName");
                long objectiveId = Integer.parseInt(requestParameters.get("objectiveId"));


                objectiveDao.changeStatus(statusName, objectiveId);

                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/editObjectiveStatus\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;

            }

            String statusCode = "200";
            String location = requestParameters.get("location");
            String body = getBody();

            outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +

                    "Content-length: " + body.length() + "\r\n" +
                    (location != null ? "Location: " + location + "\r\n" : "") +
                    "\r\n" +
                    body).getBytes());
        } catch (SQLException e) {
            Logger.error("While handling request{}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.1 500 Internal server error\r\n" +
                    "Content-length: " + message.length() + "\r\n" +
                    "\r\n" +
                    message).getBytes());
        }
    }

    public String getBody() throws SQLException {
        return objectiveDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>%s</option>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
    }
    }
