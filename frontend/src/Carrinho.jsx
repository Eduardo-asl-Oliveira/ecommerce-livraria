import { useNavigate } from "react-router-dom";

// 1. AQUI: Adicionamos o 'token' e o 'cliente' nas propriedades!
function Carrinho({ carrinho, adicionarCarrinho, removerDoCarrinho, limparCarrinho, token, cliente }){ 

    const navigate = useNavigate(); // 2. CORRIGIDO: Estava 'navagate'

    let total = 0;
    carrinho.forEach(livro => {
        total += livro.preco;
    });

    if (carrinho.length === 0) { 
        return (
            <div style={{padding:'50px', textAlign: 'center'}}>
                <h1>Carrinho</h1>
                <h3>Seu carrinho está vazio... 😔</h3>
            </div>
        );
    }

    function finalizarCompra(){
        // 3. TRAVA DE SEGURANÇA: Se não tem token, não deixa comprar!
        if (!token) {
            alert("Você precisa fazer login para finalizar a compra!");
            navigate('/login');
            return;
        }

        const itensRequest = carrinho.map(livro => { 
            return {
                quantidade: 1,
                livro: { id: livro.id }
            };
        });

        const pacotePedido = {

            cliente: { email: cliente.email }, 
            itensPedidos: itensRequest
        };

        fetch("http://localhost:8080/pedidos",{ 
             method: 'POST', 
             headers: {
                 'Content-Type': 'application/json',
                 // 4. A MÁGICA: Mostrando o crachá para o porteiro do Java
                 'Authorization': `Bearer ${token}` 
             },
             body: JSON.stringify(pacotePedido) 
        })
        .then(response => {
            if(response.ok){
                alert("Compra finalizada com sucesso! 🎉");
                limparCarrinho();
                navigate('/perfil'); 
            } else {
                alert("Erro ao realizar a compra! O servidor recusou.");
            }
        })
        .catch(erro => console.error("erro:", erro));
    }

    return (
        <div style={{display:'flex',flexDirection:'column', gap:'15px', padding:'20px'}}>
            <h2>Seus Livros:</h2>
            
            {carrinho.map((livro, index) => (
                <div key={index} style={{border:'1px solid black', padding:'10px'}}> 
                    <h3>{livro.titulo}</h3>
                    <h4>{livro.autor}</h4>
                    <h4 style={{color:'green'}}>R${livro.preco.toFixed(2)}</h4>
                    <button onClick={() => removerDoCarrinho(livro)} 
                        style={{
                            backgroundColor: '#e74c3c', 
                            color: 'white',
                            border: 'none',
                            borderRadius: '50%', 
                            width: '35px',
                            height: '35px',
                            fontSize: '18px',
                            fontWeight: 'bold',
                            cursor: 'pointer',
                            boxShadow: '0 2px 4px rgba(0,0,0,0.2)'
                        }}
                    >
                        -
                    </button>
                    <button onClick={() => adicionarCarrinho(livro)}
                        style={{
                            backgroundColor: '#3498db', 
                            color: 'white',
                            border: 'none',
                            borderRadius: '50%',
                            width: '35px',
                            height: '35px',
                            fontSize: '18px',
                            fontWeight: 'bold',
                            cursor: 'pointer',
                            boxShadow: '0 2px 4px rgba(0,0,0,0.2)'
                        }}
                    >
                        +
                    </button >
                </div>
            ))}
            
            <button onClick={() => finalizarCompra()} style={{padding:'15px',backgroundColor:'#27ae60',color:'white',border:'none',cursor:'pointer'}}>
                Finalizar Compra (R$ {total.toFixed(2)})
            </button>
        </div>
    );
}

export default Carrinho;