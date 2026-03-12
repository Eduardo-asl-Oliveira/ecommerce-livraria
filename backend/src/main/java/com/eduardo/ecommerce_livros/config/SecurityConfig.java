package com.eduardo.ecommerce_livros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Liga o CORS puxando as regras da máquina lá de baixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 2. Desliga o CSRF (Não precisamos, pois vamos usar Tokens JWT)
                .csrf(csrf -> csrf.disable())
                
                // 3. Define a API como Stateless (Esquece o usuário, ele tem que mandar o Token toda vez)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 4. As Regras da Porta (A ordem importa muito aqui!)
                .authorizeHttpRequests(req -> {
                    // --- ÁREA PÚBLICA (Visitantes não precisam de crachá) ---
                    req.requestMatchers(HttpMethod.GET, "/livros").permitAll();     // Ver vitrine
                    
                    // 👉 NOVA REGRA: Libera ver os detalhes de QUALQUER livro (1, 2, 5...)
                    req.requestMatchers(HttpMethod.GET, "/livros/**").permitAll();  
                    
                    req.requestMatchers(HttpMethod.POST, "/clientes").permitAll();  // Criar conta
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();     // Fazer login
                    
                    // 👉 A BALA DE PRATA DO CORS: Deixa o espião do navegador (OPTIONS) passar!
                    req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    
                    // --- ÁREA RESTRITA (O Cadeado) ---
                    // IMPORTANTE: Tem que ser a última linha desse bloco!
                    req.anyRequest().authenticated(); 
                })
                .addFilterBefore(securityFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Gerente de Autenticação (Usado pelo seu AutenticacaoController para conferir a senha)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Máquina de Criptografia (Usada para embaralhar a senha nova e ler a senha velha no banco)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração global do CORS (Ensina o Porteiro a conversar com o React)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Quem pode acessar? A porta exata do seu React
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        
        // Quais métodos o React pode usar? (O OPTIONS é o espião do navegador)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Quais cabeçalhos ele pode mandar? (Tudo, inclusive o nosso Authorization com o Token)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permite o envio de credenciais (útil para os Tokens)
        configuration.setAllowCredentials(true);
        
        // Aplica essa regra de liberação para TODAS as rotas da sua API (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}