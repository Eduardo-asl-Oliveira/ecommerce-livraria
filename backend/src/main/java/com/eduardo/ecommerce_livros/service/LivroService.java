package com.eduardo.ecommerce_livros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ecommerce_livros.model.Livro;
import com.eduardo.ecommerce_livros.repository.LivroRepository;

@Service
public class LivroService {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    @Autowired
    LivroRepository repository;

    public Livro salvarLivroDoGoogle(String nome){

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + nome.replace(" ", "+");
        String json = consumoApi.obterDados(url);


        LivroGoogleDTO resultado = converteDados.obterDados(json, LivroGoogleDTO.class);

        var primeiro = resultado.items().get(0).volumeInfo();

        
        Livro livro = new Livro();
        livro.setTitulo(primeiro.title());
        livro.setAutor(primeiro.title());
        livro.setPreco(50.0);

        return repository.save(livro);
        
    }
}
