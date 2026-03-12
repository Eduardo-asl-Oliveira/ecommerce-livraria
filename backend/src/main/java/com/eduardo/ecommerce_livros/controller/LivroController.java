package com.eduardo.ecommerce_livros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.ecommerce_livros.model.Livro;
import com.eduardo.ecommerce_livros.repository.LivroRepository;
import com.eduardo.ecommerce_livros.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroRepository repository;

    //metodo de listar (GET)
    @GetMapping
    public List<Livro> listar(){
        return repository.findAll();
    }

    //cadastrar livro (CREATE)
    @PostMapping
    public Livro cadastrar(@RequestBody Livro livro){
        return repository.save(livro);
    }

    //deletar livro (DELETE)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable long id){
        repository.deleteById(id);
    }

    //atualizar (UPDATE)
    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable long id, @RequestBody Livro livro){
        livro.setId(id);
        return repository.save(livro);
    }

    //perquisar livro por id
    @GetMapping("/{id}")
    public Optional<Livro> pesquisar(@PathVariable long id){
        return repository.findById(id);
    }

    //pesquisar livro pelo nome
    @GetMapping("/buscar/{titulo}")
    public List<Livro> perquisaPorNome(@PathVariable String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }
    

    @GetMapping("/importar")
    public Livro livroService(@RequestParam String titulo){
        return livroService.salvarLivroDoGoogle(titulo);
    }

}

