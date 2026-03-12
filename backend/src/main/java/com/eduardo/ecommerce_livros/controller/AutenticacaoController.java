package com.eduardo.ecommerce_livros.controller;

import com.eduardo.ecommerce_livros.model.Cliente;
import com.eduardo.ecommerce_livros.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    // 1. DTO para receber os dados do React
    public record DadosLogin(String email, String senha) {}

    // 2. DTO para devolver o Token no formato JSON: {"token": "o-codigo-gigante"}
    public record DadosTokenJWT(String token) {}

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosLogin dados) {
        // Pega o email e a senha que o React mandou e empacota para o Segurança
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        
        // O Gerente vai no banco de dados (usando o AutenticacaoService) e confere se a senha bate!
        var authentication = manager.authenticate(authenticationToken);

        // Se a senha bater, nós chamamos a Máquina de Crachás!
        var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());

        // Devolvemos o crachá novinho para o React
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}