package com.eduardo.ecommerce_livros.controller;

import com.eduardo.ecommerce_livros.model.Cliente;
import com.eduardo.ecommerce_livros.model.DadosLogin;
import com.eduardo.ecommerce_livros.model.TipoUsuario;
import com.eduardo.ecommerce_livros.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


@RestController // <--- Isso diz pro Spring: "Eu sou um site!"
@RequestMapping("/clientes") // <--- Isso cria o endereço http://.../clientes
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder
    
    ;

    // Listar todos os clientes (GET)
    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

   // Cadastrar novo cliente (POST)
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        
        // 1. Pega a senha que veio do React e passa no "liquidificador" do BCrypt
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
        
        // 2. Garante que ninguém consegue se cadastrar como ADMIN pela internet (segurança!)
        cliente.setTipo(TipoUsuario.CLiENTE); // Verifique se está escrito "USER" no seu Enum

        // 3. Agora sim, salva no banco com a senha embaralhada!
        return repository.save(cliente);
    }

    //perquisar cliete
    @GetMapping("/{id}")
    public Optional<Cliente> pesquisar(@PathVariable long id){
        return repository.findById(id);
    }

    @PostMapping("/login")
    public ResponseEntity <String> fazerLogin(@RequestBody DadosLogin login) {
       Optional<Cliente>ClientePesquisado = repository.findByEmail(login.email());
        if(ClientePesquisado.isPresent() && ClientePesquisado.get().getSenha().equals(login.senha()))
            return ResponseEntity.ok("login realizado com sucesso! Bem vindo " + ClientePesquisado.get().getNome() );
        return ResponseEntity.status(401).body("acesso negado! Senha ou email invalidos");
    }
    

    //deletar cliente 
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable long id){
        repository.deleteById(id);
    }

    //atualizar cliente 
    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizando){
        clienteAtualizando.setId(id);
        return repository.save(clienteAtualizando);
    } 

}