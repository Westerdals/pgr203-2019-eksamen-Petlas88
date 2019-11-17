package no.kristiania.eksamen2019.HTTP;

import no.kristiania.eksamen2019.Server.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EchoHttpController implements HttpController {
    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters, String body, OutputStream outputStream) throws IOException {
        if(requestAction.equals("POST")){
            requestParameters = HttpServer.parseRequestParameters(body);
        }
        String statusCode = requestParameters.getOrDefault("status", "200");
        String location = requestParameters.get("location");
        String responseBody = requestParameters.getOrDefault("body", "Hello World!");
        String contentType = requestParameters.getOrDefault("content-type","text/html");

        outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                "Content-type: " + contentType + "\r\n" +
                "Content-length: " + responseBody.length() + "\r\n" +
                (location != null ? "Location: " + location + "\r\n" : "") +
                "\r\n" +
                responseBody).getBytes());
    }
}

