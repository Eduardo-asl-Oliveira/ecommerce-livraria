import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

/*o usestate é uma variavel em que toda vez q o seu valor mudar, o react carrega novamente
 sintaxe: const [variavel, funcao_que_muda_a_variavel] = useState([])

 ja que o useState att o react toda vez que ocorre uma mudanca de valor, para evitar loops,
 usamos o useEfect para dizer: faca isso apenas uma vez, quando a tela abrir

   useEffect(() => { //codigo }, []);


o .map() recebe a lista de dados e devolve um componente visual

*/


function Vitrine({adicionarCarrinho}){
        //o estado comeca vazio
        const [livros, setLivros] = useState([]);
        const [busca, setBusca] = useState('');

        const navegar = useNavigate();

        //BUSCA OS LIVROS
        useEffect(() => {
            fetch('http://localhost:8080/livros')
                            //transforma a resposta em json
                            .then(resposta => resposta.json())
                            //os dados recebido"dados" vao para livros com o setLivros
                            .then(dados=> {
                                setLivros(dados);
                            })
                            .catch(erro=> console.error("erro ao conectar com o java",erro));
        }, [] );



        //BARRA DE PESQUISA
        //busca todos os livros e verifica se o titulo de cada livro (em letra minuscula) é igual a busca (em minusculo) e armazena na variavel
        const encontrados = livros.filter( livro => livro.titulo.toLowerCase().includes(busca.toLowerCase()));


        




        return (
            // Caixa principal de toda a página
            <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
                
                <h1 style={{ textAlign: 'center', marginBottom: '30px' }}>Minha livraria</h1>



                {/* BARRA DE PESQUISA    
                    text -> desenha uma barra de texto
                    value -> vai por o valor de busca, ou seja, oque esta sendo digitado 
                    onchange -> quando algum evento acontece (digita algo), pega esse valor (Evento.target.value) e manda pro setBuscas
                */}
                <div style={{
                    textAlign: 'center', marginBottom: '30px'}}>
                    <input 
                        type="text"
                        placeholder="Pesquise por um livro..."
                        value={busca}
                        onChange={(Evento) => setBusca(Evento.target.value)}
                        style={{
                            padding: '12px 20px',
                            width: '50%',
                            border: '2px solid #bdc3c7',
                            borderRadius: '25px', 
                            fontSize: '1.1rem',
                            outline: 'none'
                        }}
                    />
                </div>


                {/* CAIXA DO GRID*/}
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
                    gap: '20px'
                }}>
                    
                    {encontrados.map(livro => (
                        <div key={livro.id} style={{
                            border: '1px solid black',
                            borderRadius: '10px',
                            padding: '15px',
                            textAlign: 'center',
                            boxShadow: '0 4px 6px rgba(0,0,0,0.05)',
                            display: 'flex',
                            flexDirection: 'column',
                            justifyContent: 'space-between'
                        }}>

                            <img
                                src={livro.imagem}
                                alt={livro.titulo}
                                onError={(e) => { 
                                    e.target.src = 'https://placehold.co/300x450/dedede/555555?text=Capa+Indisponível'; 
                                }}
                                style={{ width: '100%', borderRadius: '5px', marginBottom: '15px' }}
                            />
                            
                            <div>
                                <h3 style={{ fontSize: '1.1rem', margin: '0 0 10px 0' }}>{livro.titulo}</h3>
                                <h4 style={{ color: '#7f8c8d', margin: '0 0 15px 0' }}>{livro.autor}</h4>
                            </div>

                            <div>
                                <h2 style={{ color: '#27ae60', margin: '0 0 15px 0' }}>
                                    R$ {livro.preco.toFixed(2).replace(".", ",")}
                                </h2>

                                <button 
                                    onClick={() => navegar('/livro/' + livro.id)}
                                    style={{
                                        backgroundColor: '#3498db',
                                        color: 'white',
                                        border: 'none',
                                        padding: '10px 20px',
                                        borderRadius: '5px',
                                        cursor: 'pointer',
                                        width: '100%',
                                        fontWeight: 'bold'
                                    }}
                                >
                                    Ver mais...
                                </button>
                            </div>

                            <button 
                                onClick={() => adicionarCarrinho(livro)} 
                                style={{
                                    backgroundColor: '#27ae60', // Um verde bonito para compra
                                    color: 'white',
                                    border: 'none',
                                    padding: '10px 20px',
                                    borderRadius: '5px',
                                    cursor: 'pointer',
                                    width: '100%',
                                    fontWeight: 'bold',
                                    marginTop: '10px' // Dá um espacinho do botão de cima
                                }}
                            >
                                🛒 Adicionar ao Carrinho
                            </button>
                        </div>
                    ))}
                    
                </div>
                
            </div>
        );
    }
    export default Vitrine;
