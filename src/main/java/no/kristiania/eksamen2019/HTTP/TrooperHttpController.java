package no.kristiania.eksamen2019.HTTP;


import no.kristiania.eksamen2019.DAO.Trooper;
import no.kristiania.eksamen2019.DAO.TrooperDao;
import no.kristiania.eksamen2019.Server.HttpServer;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class TrooperHttpController implements HttpController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(TrooperHttpController.class);
    private TrooperDao trooperDao;


    public TrooperHttpController(TrooperDao trooperDao) {
        this.trooperDao = trooperDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters,
                       String requestBody, OutputStream outputStream) throws IOException {
        try {
            if (requestAction.equalsIgnoreCase("POST")) {

                requestParameters = HttpServer.parseRequestParameters(requestBody);
                Trooper trooper = new Trooper();

                String name = URLDecoder.decode(requestParameters.get("name"));
                String email = URLDecoder.decode(requestParameters.get("email"));
                String role = URLDecoder.decode(requestParameters.get("role"));

                trooper.setName(name);
                trooper.setEmail(email);
                trooper.setRole(role);

                trooperDao.insert(trooper);

                //Respond
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/newWorker.html\r\n" +
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
        return trooperDao.listAll().stream()
                .map(p -> String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>", p.getId(), p.getName(),
                        p.getEmail(), p.getRole())).collect( Collectors.joining(""));
    }
}
