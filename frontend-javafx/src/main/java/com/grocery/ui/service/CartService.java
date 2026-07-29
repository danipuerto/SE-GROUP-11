package com.grocery.ui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CartService {

    private static final int CUSTOMER_ID = 1;
    private static final String BASE_URL = "http://localhost:8080";

    private final HttpClient httpClient;

    public CartService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void addToCart(int productId)
            throws IOException, InterruptedException {

        String url = BASE_URL
                + "/cart/"
                + CUSTOMER_ID
                + "/add/"
                + productId
                + "?quantity=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {
            throw new IOException(
                    "Backend returned status "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }
    }
}
