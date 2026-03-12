import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({ setClienteLogado, setToken }) {
    // Estados para guardar o que o usuário digita nos campos
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const navigate = useNavigate();

    const lidarComLogin = (evento) => {
        evento.preventDefault(); // Impede a página de recarregar (padrão do HTML)

        // Bate na porta do nosso Spring Boot
        fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            // Empacota o email e senha no formato que o Java espera (DadosLogin)
            body: JSON.stringify({ email: email, senha: senha })
        })
        .then(resposta => {
            if (resposta.ok) {
                return resposta.json(); // Se for 200 OK, extrai o JSON com o Token
            }
            throw new Error('Email ou senha incorretos!'); // Se for 403, avisa o erro
        })
        .then(dados => {
            console.log("PACOTE DO JAVA:", dados);
            // 1. Guarda o crachá no estado global do App.jsx
            setToken(dados.token);
            
            // 2. Avisa o sistema que tem alguém logado! 
            // (Como o Java só devolveu o token, vamos improvisar o cliente por enquanto)
            setClienteLogado({ email: email, nome: "Usuário Logado" });

            alert("Bem-vindo de volta!");
            navigate('/perfil'); // Manda o cliente para o perfil
        })
        .catch(erro => {
            alert(erro.message);
        });
    };

    return (
        <div style={{ maxWidth: '400px', margin: '50px auto', padding: '20px', border: '1px solid gray', borderRadius: '8px' }}>
            <h2>Acesse sua conta</h2>
            
            <form onSubmit={lidarComLogin} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                <div>
                    <label>Email:</label><br/>
                    <input 
                        type="email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)} 
                        required 
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>
                
                <div>
                    <label>Senha:</label><br/>
                    <input 
                        type="password" 
                        value={senha} 
                        onChange={(e) => setSenha(e.target.value)} 
                        required 
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>

                <button type="submit" style={{ padding: '10px', backgroundColor: '#007BFF', color: 'white', border: 'none', cursor: 'pointer' }}>
                    Entrar
                </button>
            </form>
        </div>
    );
}

export default Login;