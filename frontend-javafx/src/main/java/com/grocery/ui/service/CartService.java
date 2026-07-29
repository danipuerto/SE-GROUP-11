package com.grocery.ui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.ui.model.CartItem;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CartService {

    private static final String BASE_URL = "http://localhost:8080";
    private static final int CUSTOMER_ID = 1;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CartService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void addToCart(int productId)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        BASE_URL
                                + "/cart/"
                                + CUSTOMER_ID
                                + "/add/"
                                + productId
                                + "?quantity=1"
                ))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            throw new IOException(
                    "Could not add item. HTTP status: "
                            + response.statusCode()
            );
        }
    }

    public List<CartItem> getCartItems()
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        BASE_URL + "/cart/" + CUSTOMER_ID
                ))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "Could not load cart. HTTP status: "
                            + response.statusCode()
            );
        }

        return objectMapper.readValue(
                response.body(),
                new TypeReference<List<CartItem>>() {
                }
        );
    }

    public void removeItem(int productId)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        BASE_URL
                                + "/cart/"
                                + CUSTOMER_ID
                                + "/remove/"
                                + productId
                ))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            throw new IOException(
                    "Could not remove item. HTTP status: "
                            + response.statusCode()
            );
        }
    }
}
