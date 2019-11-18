package no.kristiania.eksamen2019.HTTP;


import no.kristiania.eksamen2019.DAO.ObjectiveStatus;
import no.kristiania.eksamen2019.DAO.ObjectiveStatusDao;
import no.kristiania.eksamen2019.Server.HttpServer;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectiveStatusHttpController implements HttpController {
    private ObjectiveStatusDao objectiveStatusDao;
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ObjectiveStatusHttpController.class);

    public ObjectiveStatusHttpController(ObjectiveStatusDao objectiveStatusDao) {

        this.objectiveStatusDao = objectiveStatusDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters, String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {

                requestParameters = HttpServer.parseRequestParameters(requestBody);
                ObjectiveStatus status = new ObjectiveStatus();

                String name = URLDecoder.decode(requestParameters.get("statusName"));

                status.setName(name);

                objectiveStatusDao.insert(status);

                //Respond
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/newStatus.html\r\n" +
                        "Connection: close\r\n" +
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
        String body = objectiveStatusDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>%s</option>", p.getName(), p.getName()))
                .collect(Collectors.joining(""));
        return body;
    }


}