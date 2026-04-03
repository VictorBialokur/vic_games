package br.com.victor.vicgamesconsole.client;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class ClientHttpConfiguration {

    public HttpResponse<String> dispararRequisicaoGet(String uri) throws RuntimeException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> dispararRequisicaoPost(String uri, Object object) throws RuntimeException {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(object);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> dispararRequisicaoPut(String uri, Object object) throws RuntimeException {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(object);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> dispararRequisicaoDelete(String uri, Object object) throws RuntimeException {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(object);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
