package no.kristiania.eksamen2019.HTTP;


import no.kristiania.eksamen2019.DAO.ObjectiveTrooper;
import no.kristiania.eksamen2019.DAO.ObjectiveTrooperDao;
import no.kristiania.eksamen2019.Server.HttpServer;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectiveTrooperHttpController implements HttpController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(TrooperHttpController.class);
    private ObjectiveTrooperDao trooperDao;


    public ObjectiveTrooperHttpController(ObjectiveTrooperDao trooperDao) {
        this.trooperDao = trooperDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters,
                       String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {

                requestParameters = HttpServer.parseRequestParameters(requestBody);
                ObjectiveTrooper objectiveTrooper = new ObjectiveTrooper();

                String name = URLDecoder.decode(requestParameters.get("memberName"));
                String email = URLDecoder.decode(requestParameters.get("email"));

                objectiveTrooper.setName(name);
                objectiveTrooper.setEmail(email);

                trooperDao.insert(objectiveTrooper);

                //Respond
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;
            }

            String statusCode = "200";
            String location = requestParameters.get("location");
            String body = getBody();
            //requestParameters.getOrDefault("body", getBody());

            outputStream.write(("HTTP/1.1 " + statusCode + " OK\r\n" +
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
        String body = trooperDao.listAll().stream()
                .map(p -> String.format("<tr> <td>%s</td> <td>%s</td> </tr>", p.getName(), p.getEmail()))
                .collect( Collectors.joining(""));
        return body;
    }
}
