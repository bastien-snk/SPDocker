package dev.fls.utils;

import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicReference;

public class HttpRequest {

    private static HttpClient httpClient = createClient();

    /**
     * Create a HTTP Client to send HTTP Requests
     *
     * @return
     */
    public static HttpClient createClient() {
        return HttpClient
                .newBuilder()
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
    }

    public static HttpResponse<String> request(RequestType requestType, URI uri, String body) {

        AtomicReference<String> result = new AtomicReference<>("");

        try {
            if (httpClient == null) httpClient = createClient();

            java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(uri);

            switch (requestType) {
                case POST:
                case PUT:
                    if(body == null) body = "";
                    java.net.http.HttpRequest.BodyPublisher bodyPublisher = java.net.http.HttpRequest.BodyPublishers.ofString(body);

                    if (requestType.equals(RequestType.POST)) builder.POST(bodyPublisher);
                    if (requestType.equals(RequestType.PUT)) builder.PUT(bodyPublisher);
                    break;

                case DELETE:
                    builder.DELETE();
                    break;

                default:
                    builder.GET();
                    break;
            }

            java.net.http.HttpRequest request = builder.build();
            HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @AllArgsConstructor
    public enum RequestType {
        GET(),
        POST(),
        PUT(),
        PATCH(),
        DELETE(),
        COPY(),
        HEAD(),
        OPTIONS(),
        LINK(),
        UNLINK(),
        PURGE(),
        LOCK(),
        UNLOCK(),
        PROPFIND(),
        VIEW();
    }
}
