package no.kristiania.eksamen2019.Server;

import no.kristiania.eksamen2019.HTTP.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {
        @Test
        void mathSouldWork(){
        assertEquals(4, 2+2);
    }

        @Test
        void shouldExecuteHttprequest() throws IOException {
            HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo");
            assertEquals(200, client.execute("GET").getStatusCode());
        }

        @Test
        void shouldReadStatusCode() throws IOException {
            HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=401");
            assertEquals(401, client.execute("GET").getStatusCode());
        }

        @Test
        void shouldReadHeaders() throws IOException {
            HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?content-type=text/plain");
            assertEquals("text/plain; charset=utf-8", client.execute("GET").getHeader("Content-type"));
        }

        @Test
        void shouldReadContentLength() throws IOException {
            HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?body=hello+world!");
            assertEquals(12, client.execute("GET").getContentLenght());
        }

        @Test
        void shouldReadBody() throws IOException {
            HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?body=hello+world!");
            assertEquals("hello world!", client.execute("GET").getBody());
        }

    }

