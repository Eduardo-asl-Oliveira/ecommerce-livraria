import { useParams } from "react-router-dom";
import { useState, useEffect } from 'react';

function DetalhesLivro({ adicionarCarrinho }) {
    const { id } = useParams();
    const [livro, setLivro] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/livros/" + id)
            .then(resposta => resposta.json())
            .then(dados => {
                console.log("RESPOSTA DO JAVA:", dados);
                setLivro(dados);
            })
            .catch(erro => console.error("erro", erro));
    }, [id]);

    if (livro === null) {
        return <h2 style={{ textAlign: 'center', marginTop: '50px' }}>Carregando detalhes do livro... ⏳</h2>;
    }

    return (
        
        <div style={{
            maxWidth: '500px', // Segura a largura para não esticar na tela toda
            margin: '40px auto', // Centraliza a caixa na tela e dá uma margem no topo
            padding: '30px',
            fontFamily: 'sans-serif',
            border: '1px solid #e0e0e0', // Bordinha cinza bem sutil
            borderRadius: '10px',
            boxShadow: '0 4px 8px rgba(0,0,0,0.05)', // Sombra quase invisível para dar profundidade
            backgroundColor: '#fff'
        }}>
            
            
            <h1 style={{ textAlign: 'center', color: '#333', marginBottom: '5px' }}>
                {livro.titulo}
            </h1>

            <h3 style={{ textAlign: 'center', color: '#666', marginTop: '0', marginBottom: '25px', fontWeight: 'normal' }}>
                Autor: {livro.autor}
            </h3>

            <div style={{ textAlign: 'center' }}>
                <img
                    src={livro.imagem}
                    alt={livro.titulo}
                    style={{
                        maxWidth: '220px', // Evita que a capa fique gigante
                        height: 'auto',
                        borderRadius: '4px',
                        boxShadow: '3px 4px 8px rgba(0,0,0,0.2)', // Dá um efeito 3d de livro físico
                        marginBottom: '25px'
                    }}
                />
            </div>

            <button
                onClick={() => adicionarCarrinho(livro)}
                style={{
                    backgroundColor: '#27ae60',
                    color: 'white',
                    border: 'none',
                    padding: '12px 20px',
                    borderRadius: '5px',
                    cursor: 'pointer',
                    width: '100%',
                    fontWeight: 'bold',
                    fontSize: '1.1em',
                    boxShadow: '0 2px 4px rgba(39, 174, 96, 0.4)' // Sombra verdinha no botão
                }}
            >
                🛒 Adicionar ao Carrinho
            </button>

        </div>
    );
}

export default DetalhesLivro;