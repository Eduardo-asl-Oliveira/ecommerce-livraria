package com.eduardo.ecommerce_livros.service;

import com.eduardo.ecommerce_livros.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // O Spring manda o "username" (email), nós buscamos a "caixa" (Optional) no banco.
        // Se a caixa estiver vazia, lançamos o erro do Spring Security.
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o email: " + username));
    }
}
