package com.eduardo.ecommerce_livros.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;


@Entity //diz que é uma tabela
@Table(name = "tb_clientes") //da nome a tabela
public class Cliente implements UserDetails{

    @Id //chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,  nullable = false)
    private String email;

    private String senha;
    private String nome;

    // mappedBy = "cliente" avisa: "Quem manda na relação é o atributo 'cliente' lá na outra classe"
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    public Cliente(){}

    public Cliente(Long id, String email, String senha, String nome, List<Endereco> enderecos, TipoUsuario tipo) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.enderecos = enderecos;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        // Se o usuário for ADMIN, ele tem poder de ADMIN e de USER
        if (this.tipo == TipoUsuario.ADMIN) { // Confirme se no seu Enum a palavra é ADMIN mesmo
            return java.util.List.of(
                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"),
                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")
            );
        } 
        
        // Se for qualquer outra coisa (cliente normal), ele só tem poder de USER
        return java.util.List.of(
            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")
        );
    }
    @Override
    public String getPassword() {
        return this.senha; // O Spring pergunta a senha, nós entregamos a senha
    }

    @Override
    public String getUsername() {
        return this.email; // O Spring pergunta o login, nós dizemos que é o email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}