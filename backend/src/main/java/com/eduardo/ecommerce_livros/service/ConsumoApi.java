package com.eduardo.ecommerce_livros.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
//AIzaSyCrtVqYpTLaK29wj8Sl15apDiWzsf1aDkE
    public String obterDados(String url) {
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body(); // Retorna o JSON puro (texto)
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar dados: " + e.getMessage());
        }
    }
}
