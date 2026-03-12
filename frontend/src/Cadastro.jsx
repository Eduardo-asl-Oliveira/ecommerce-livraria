import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

function Cadastro() {
    // Estados para os campos do formulário
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    
    const navigate = useNavigate();

    const lidarComCadastro = (evento) => {
        evento.preventDefault(); // Impede a página de piscar

        // Monta o "pacote" com os dados exatamente como o Java espera na classe cliente
        const novoCliente = {
            nome:nome,
            email:email,
            senha:senha
        };

        // Bate na porta do clienteController no Java
        fetch('http://localhost:8080/clientes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(novoCliente)
        })
        .then(resposta => {
            if (resposta.ok) {
                alert("Conta criada com sucesso! 🎉 Agora faça o seu login.");
                navigate('/login'); // Manda o usuário para a tela de login
            } else {
                throw new Error("Erro ao criar conta. Talvez o email já exista!");
            }
        })
        .catch(erro => {
            alert(erro.message);
        });
    };

    return (
        <div style={{maxWidth:'400px',margin: '50px auto', padding:'30px',border:'1px solid #e0e0e0',borderRadius: '10px', boxShadow:'0 4px 8px rgba(0,0,0,0.05)',backgroundColor:'#fff'}}>
            <h2 style={{textAlign:'center', marginBottom: '20px'}}>Criar Nova Conta</h2>
            
            <form onSubmit={lidarComCadastro} style={{display: 'flex',flexDirection:'column',gap: '15px'}}>
                <div>
                    <label style={{fontWeight:'bold'}}>Nome Completo:</label><br/>
                    <input 
                        type="text" 
                        value={nome} 
                        onChange={(e) => setNome(e.target.value)} 
                        required 
                        style={{width:'100%',padding:'10px',borderRadius:'5px',border:'1px solid #ccc',marginTop:'5px'}}
                    />
                </div>

                <div>
                    <label style={{fontWeight:'bold'}}>Email:</label><br/>
                    <input 
                        type="email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)} 
                        required 
                        style={{width:'100%',padding:'10px', borderRadius: '5px',border:'1px solid #ccc', marginTop:'5px'}}
                    />
                </div>
                
                <div>
                    <label style={{fontWeight: 'bold'}}>Senha:</label><br/>
                    <input 
                        type="password" 
                        value={senha} 
                        onChange={(e) => setSenha(e.target.value)} 
                        required 
                        style={{width:'100%', padding:'10px',borderRadius:'5px', border:'1px solid #ccc', marginTop:'5px'}}
                    />
                </div>

                <button type="submit" style={{padding:'12px', backgroundColor:'#27ae60', color:'white', border:'none', borderRadius:'5px', cursor:'pointer', fontWeight:'bold', fontSize:'1.1em', marginTop:'10px'}}>
                    Cadastrar
                </button>
            </form>

            <div style={{ textAlign: 'center', marginTop: '20px' }}>
                <p>Já tem uma conta? <Link to="/login" style={{ color:'#007BFF',textDecoration:'none', fontWeight:'bold'}}>Faça login aqui</Link></p>
            </div>
        </div>
    );
}

export default Cadastro;