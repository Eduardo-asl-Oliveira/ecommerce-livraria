package com.eduardo.ecommerce_livros.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eduardo.ecommerce_livros.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //assinatura
    private String secret = "minha-senha-secreta-super-segura-hiter-2026";

    // MÉTODO 1: Cria o crachá na hora do Login
    public String gerarToken(Cliente cliente) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ecommerce-livros")
                    .withSubject(cliente.getEmail())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o crachá (token JWT)", exception);
        }
    }

    // MÉTODO 2: Lê o crachá quando o React manda de volta
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("ecommerce-livros")
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); // Devolve o Email do usuário
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    // Regra da empresa: O crachá vence em 2 horas.
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}