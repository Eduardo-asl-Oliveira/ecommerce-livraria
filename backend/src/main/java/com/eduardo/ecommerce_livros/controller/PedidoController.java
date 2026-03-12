package com.eduardo.ecommerce_livros.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 

import com.eduardo.ecommerce_livros.model.Pedido;
import com.eduardo.ecommerce_livros.repository.LivroRepository;
import com.eduardo.ecommerce_livros.model.ItemPedido; 
import com.eduardo.ecommerce_livros.model.Livro;
import com.eduardo.ecommerce_livros.repository.PedidoRepository;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.eduardo.ecommerce_livros.model.ItemPedidoDTO;
import com.eduardo.ecommerce_livros.model.RespostaPedidoDTO;
import com.eduardo.ecommerce_livros.model.StatusPedido;


@RestController
@RequestMapping("/pedidos") // Dica: Use plural "/pedidos" (padrão de mercado)
public class PedidoController {

    

    private final LivroRepository livroRepository;

    @Autowired
    private PedidoRepository repository;

    PedidoController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

@PostMapping
    public Pedido recebePedido(@RequestBody Pedido pedido) {

        // 1. Pega o usuário logado que o nosso Inspetor validou pelo Crachá (Token)
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        com.eduardo.ecommerce_livros.model.Cliente clienteRealDoBanco = (com.eduardo.ecommerce_livros.model.Cliente) authentication.getPrincipal();

        // 2. Cola esse cliente verdadeiro no pedido (Isso acalma o Hibernate na hora!)
        pedido.setCliente(clienteRealDoBanco);

        double total = 0.0;
        pedido.setMomentoDoPedido(Instant.now());

        for (ItemPedido item : pedido.getItensPedidos()) {
            Long idLivro = item.getLivro().getId();
            
            // Buscamos o livro verdadeiro no banco
            Livro livroReal = livroRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

            // Verificação de estoque
            if(item.getQuantidade() > livroReal.getEstoque()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para o livro: " + livroReal.getTitulo());
            }

            Integer novoEstoque = livroReal.getEstoque() - item.getQuantidade();
            livroReal.setEstoque(novoEstoque); 
            livroRepository.save(livroReal);   // Salva o livro atualizado

            
            Double precoDoLivro = livroReal.getPreco();
            item.setPrecoUnitario(precoDoLivro); 
            
            total += precoDoLivro * item.getQuantidade(); 
            item.setPedido(pedido); 
        }

        pedido.setTotal(total);
        return repository.save(pedido);
    }

    @DeleteMapping("/{id}")
    public void deletarPedido(@PathVariable Long id){
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não existe!"));
        

        if(pedido.getStatus() != StatusPedido.AGUARDANDO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cancelar. Status atual: " + pedido.getStatus());
        }

        for (ItemPedido item : pedido.getItensPedidos()) {
            Long idLivro = item.getLivro().getId();
            Livro livroReal = livroRepository.findById(idLivro).get();
                    
            Integer novoEstoque = livroReal.getEstoque() +  item.getQuantidade();
            livroReal.setEstoque(novoEstoque);
            livroRepository.save(livroReal);
        }
        repository.delete(pedido);
    }

    @GetMapping
    public List<Pedido> listar(){
        return repository.findAll();
    }

    // buscar histórico
    @GetMapping("/cliente/{id}")
    public List<RespostaPedidoDTO> listarPedidosDoCliente(@PathVariable Long id) {
        
        List<Pedido> pedidosPadrao = repository.findByClienteId(id);

        return pedidosPadrao.stream()
                .map(p -> new RespostaPedidoDTO(
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getCliente().getEmail(),
                        p.getTotal(),
                        p.getStatus(),
                        p.getItensPedidos().stream()
                                .map(i -> new ItemPedidoDTO(
                                    i.getLivro().getTitulo(),
                                    i.getQuantidade(),
                                    i.getPrecoUnitario()
                                ))
                                .toList()
                ))
                .toList();
    }
}