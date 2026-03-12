package com.eduardo.ecommerce_livros.config;

import com.eduardo.ecommerce_livros.repository.ClienteRepository;
import com.eduardo.ecommerce_livros.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Pega o Token do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        // 2. Se tiver um Token válido, nós forçamos o login no Spring!
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // Extrai o email
            var usuario = repository.findByEmail(subject).get(); // Busca o cliente no banco

            // Cria o passe livre para o Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 3. Manda a requisição seguir o fluxo normal
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", ""); // Tira a palavra Bearer para ler só o código
        }
        return null;
    }
}