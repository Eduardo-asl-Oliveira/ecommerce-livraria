package com.eduardo.ecommerce_livros.service;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record LivroGoogleDTO(List<Item> items) {

    public record Item(@SerializedName("volumeInfo") VolumeInfo volumeInfo) {}

    public record VolumeInfo(
        String title,
        List<String> authors,
        String publishedDate,
        @SerializedName("imageLinks") ImageLinks imageLinks
    ) {}

    public record ImageLinks(String thumbnail) {}
}