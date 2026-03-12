package com.eduardo.ecommerce_livros.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.eduardo.ecommerce_livros.model.Cliente;
import com.eduardo.ecommerce_livros.model.Endereco; // Importando o Endereco
import com.eduardo.ecommerce_livros.model.Livro;
import com.eduardo.ecommerce_livros.repository.ClienteRepository;
import com.eduardo.ecommerce_livros.repository.LivroRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final LivroRepository livroRepository;
    private final ClienteRepository clienteRepository;

    public DatabaseSeeder(LivroRepository livroRepository, ClienteRepository clienteRepository) {
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        
        // 1. Criando o Cliente e seu Endereço se o banco estiver vazio
        if (clienteRepository.count() == 0) {
            System.out.println("👤 Criando cliente padrão...");
            
            Cliente cliente = new Cliente();
            cliente.setNome("Eduardo");
            cliente.setEmail("eduardo@teste.com.br");

            Endereco endereco = new Endereco();
            // ATENÇÃO: Ajuste "setRua" para o nome do atributo que está na sua classe Endereco
            endereco.setRua("Usina Cocal Narandiba"); 
            
            // Vinculando o endereço ao cliente
            cliente.setEnderecos(List.of(endereco));
            cliente.setSenha(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("123456"));
            
            clienteRepository.save(cliente);
        }

        // 2. Criando os Livros com Estoque
        if (livroRepository.count() <= 5) {
            System.out.println("🌱 Banco vazio! Inserindo 20 livros na vitrine...");

            List<Livro> cargaInicial = List.of(
                // Programação
                criarLivro("Entendendo o React e Hooks", "Eduardo Developer", 85.00, "https://covers.openlibrary.org/b/isbn/9781491952023-L.jpg"),
                criarLivro("Dominando o Spring Boot", "Java Experts", 110.50, "https://covers.openlibrary.org/b/isbn/9781617292545-L.jpg"),
                criarLivro("Java Efetivo", "Joshua Bloch", 95.00, "https://covers.openlibrary.org/b/isbn/9780134685991-L.jpg"),
                criarLivro("Arquitetura de E-commerce", "Tech Masters", 75.90, "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg"),
                criarLivro("Clean Code", "Robert C. Martin", 90.00, "https://m.media-amazon.com/images/I/51E2055ZGUL._SY466_.jpg"), 
                
                // Engenharia e Indústria
                criarLivro("Fundamentos de Eletrônica", "M. Sadler", 120.00, "https://covers.openlibrary.org/b/isbn/9780521809269-L.jpg"),
                criarLivro("Medições com Osciloscópio e Multímetro", "Lab Tech", 65.50, "https://covers.openlibrary.org/b/isbn/9781259587542-L.jpg"),
                criarLivro("Manual de Válvulas Industriais", "Eng. Mecânica", 150.00, "https://covers.openlibrary.org/b/isbn/9780071592751-L.jpg"),
                criarLivro("Processamento de Bioenergia e Cana", "Agro Indústria", 135.20, "https://covers.openlibrary.org/b/isbn/9780124079090-L.jpg"),
                criarLivro("Gestão de Compras em Indústrias", "Supply Chain", 88.00, "https://covers.openlibrary.org/b/isbn/9780132743952-L.jpg"),
                
                // Bebidas e Coquetelaria
                criarLivro("A Arte do Gin: Botânicos e Receitas", "Barmen Co.", 55.00, "https://covers.openlibrary.org/b/isbn/9781616200466-L.jpg"),
                criarLivro("Coquetelaria Clássica", "Mestre Destilador", 62.90, "https://covers.openlibrary.org/b/isbn/9781626540644-L.jpg"),
                criarLivro("Guia Definitivo do Gin Tônica", "Mixology", 45.00, "https://covers.openlibrary.org/b/isbn/9780393089035-L.jpg"),
                
                // Esportes e Jogos
                criarLivro("Táticas de Futebol Moderno", "Coach Silva", 49.90, "https://covers.openlibrary.org/b/isbn/9781568587387-L.jpg"),
                criarLivro("Gestão de Clubes de Futebol", "Sports Manager", 79.90, "https://covers.openlibrary.org/b/isbn/9781568584812-L.jpg"),
                criarLivro("Jogos de Cartas: Do Pontinho ao Poker", "Clube de Cartas", 35.00, "https://covers.openlibrary.org/b/isbn/9780451204844-L.jpg"),
                
                // Ficção Científica e Fantasia
                criarLivro("O Senhor dos Anéis", "J.R.R. Tolkien", 65.90, "https://covers.openlibrary.org/b/isbn/9780544003415-L.jpg"),
                criarLivro("1984", "George Orwell", 40.00, "https://m.media-amazon.com/images/I/819js3EQwbL._SY466_.jpg"), 
                criarLivro("Duna", "Frank Herbert", 89.90, "https://covers.openlibrary.org/b/isbn/9780441172719-L.jpg"),
                criarLivro("O Guia do Mochileiro das Galáxias", "Douglas Adams", 42.50, "https://covers.openlibrary.org/b/isbn/9780345391803-L.jpg")
            );

            livroRepository.saveAll(cargaInicial);
            System.out.println("✅ Vitrine abastecida com 20 livros incríveis!");
        } else {
            System.out.println("📚 O banco já possui livros. Semeador ignorado.");
        }
    }

    // Método ajudante
    private Livro criarLivro(String titulo, String autor, double preco, String imagem) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setPreco(preco);
        livro.setImagem(imagem);
        livro.setEstoque(50); // O ESTOQUE ESTÁ AQUI!
          
        return livro;
    }
}