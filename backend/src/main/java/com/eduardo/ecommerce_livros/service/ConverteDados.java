package com.eduardo.ecommerce_livros.service;

import com.google.gson.Gson;

public class ConverteDados {
    
    // O Gson transforma a String (json) na Classe que você pedir (classe)
    public <T> T obterDados(String json, Class<T> classe) {
        Gson gson = new Gson();
        return gson.fromJson(json, classe);
    }
}