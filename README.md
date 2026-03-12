# 📚 Livrar - E-commerce de Livros

Um sistema completo de comércio eletrônico desenvolvido com uma arquitetura moderna, separando totalmente o Back-end (API RESTful) do Front-end (SPA). O projeto simula o fluxo real de uma loja virtual, desde a visualização da vitrine até o checkout seguro com autenticação em nível industrial.

## 🚀 Funcionalidades

* **Catálogo Dinâmico:** Listagem de livros consumida diretamente do banco de dados.
* **Sistema de Autenticação:** Cadastro de clientes e Login protegidos.
* **Segurança Robusta:** Senhas criptografadas no banco (BCrypt) e rotas protegidas por Tokens JWT (JSON Web Token).
* **Carrinho de Compras:** Gerenciamento de estado complexo no Front-end (adicionar, remover, somar total).
* **Checkout Protegido:** Fechamento de pedidos atrelado ao usuário logado, com validação de credenciais via Spring Security.
* **Área do Cliente:** Perfil exclusivo listando o histórico de compras de cada usuário.

## 💻 Tecnologias Utilizadas

**Back-end (API):**
* Java 17+
* Spring Boot
* Spring Security (Filtros de Interceptação e Autenticação Stateless)
* Autenticação JWT (JSON Web Token)
* Hibernate / Spring Data JPA
* Banco de Dados MySQL

**Front-end (Interface):**
* React.js (com Vite)
* React Router DOM (Navegação SPA)
* Gerenciamento de Estado (Hooks: `useState`, `useEffect`)
* Integração de API via `fetch`

## ⚙️ Como executar o projeto localmente

Para rodar este projeto na sua máquina, você precisará de dois terminais abertos (um para o Java e outro para o Node).

### 1. Configurando o Back-end (Java/Spring)
1. Certifique-se de ter o **Java** e o **MySQL** instalados.
2. Abra o arquivo `src/main/resources/application.properties` e configure as credenciais do seu banco de dados local:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update