import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Perfil({ cliente, token }) {
    const [pedidos, setPedidos] = useState([]);

    useEffect(() => {
        // Só tenta buscar os pedidos se o usuário estiver logado
        if (token) {
            fetch('http://localhost:8080/pedidos', {
                method: 'GET',
                headers: {
                    // mostra o cracha la
                    'Authorization': `Bearer ${token}`, 
                    'Content-Type': 'application/json'
                }
            })
            .then(resposta => {
                if (resposta.ok) return resposta.json();
                throw new Error("Sessão expirada ou não autorizada.");
            })
            .then(dados => setPedidos(dados))
            .catch(erro => console.error("Erro ao buscar pedidos:", erro));
        }
    }, [token]); // O useEffect  reage sempre que o token mudar

    // Usuário NÃO logado
    if (cliente == null) {
        return (
            <div style={{ textAlign: 'center', marginTop: '50px' }}>
                <h2>Você ainda não realizou login!</h2>
                <Link to="/login">
                    <button style={{ padding: '10px', margin: '10px', cursor: 'pointer' }}>Fazer Login</button>
                </Link>
                <Link to="/cadastro">
                    <button style={{ padding: '10px', margin: '10px', cursor: 'pointer' }}>Cadastrar Conta</button>
                </Link>
            </div>  
        );
    } 
    
    //Usuário logado, mas SEM pedidos
    if (pedidos.length === 0) {
        return (
            <div>
                <h1>Seu perfil</h1>
                <h2>Dados:</h2>
                <h3>Nome: {cliente.nome}</h3>
                <h3>Email: {cliente.email}</h3>
                <h3 style={{ color: 'gray' }}>Você não possui nenhum pedido...</h3>
            </div>
        );
    } 
    
    //Usuário logado COM pedidos
    return (
        <div> 
            <h1>Seu perfil</h1>
            <h2>Dados:</h2>
            <h3>Nome: {cliente.nome}</h3>
            <h3>Email: {cliente.email}</h3>

            <h2>Histórico de compras:</h2>

            {/* Como o Java ainda devolve todos os pedidos, e não temos o ID do cliente no React, 
                vamos mapear direto da lista de pedidos por enquanto */}
            {pedidos.map(pedido => ( 
                <div key={pedido.id} style={{ border: '1px solid gray', margin: '10px', padding: '10px' }}>
                    <h4>Data: {pedido.momentoDoPedido}</h4>
                    <h4>Total: R$ {pedido.total}</h4>
                    <h4>Status: {pedido.status}</h4>
                    
                    {pedido.itensPedidos.map(item => ( 
                        <div key={item.id} style={{ marginLeft: '20px' }}>
                            <p>Livro: {item.livro.titulo} - R$ {item.livro.preco}</p>
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
}

export default Perfil;