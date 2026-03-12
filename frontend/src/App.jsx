import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Vitrine from './Vitrine';
import DetalhesLivro from './DetalhesLivro';
import Navbar from './Navbar';
import Carrinho from './Carrinho'; 
import { useState } from 'react';
import Perfil from './Perfil';
import Login from './Login';
import Cadastro from './Cadastro';




function App() {

  const[carrinho, setCarrinho] = useState([]);
  const [clienteLogado, setClienteLogado] = useState(null);
  const [token, setToken] = useState(null);

  function adicionarCarrinho(livro){
    setCarrinho([...carrinho,livro]);
  }

  function removerDoCarrinho(idDoLivro){
    const novocarrinho = carrinho.filter(livro => livro.id !== idDoLivro);
  }

  function limparCarrinho(){
    setCarrinho([]);
  }


  return (
    <BrowserRouter>
      <Navbar carrinho={carrinho}/>
      <Routes>
        {/* Vitrine */}
        <Route path="/" element={<Vitrine adicionarCarrinho={adicionarCarrinho} />} />
        
        {/* DetalhesLivro */}
        <Route path="/livro/:id" element={<DetalhesLivro adicionarCarrinho={adicionarCarrinho} />} />

        {/* Carrinho */}
        <Route path="/carrinho" element={<Carrinho 
                                              carrinho={carrinho} adicionarCarrinho={adicionarCarrinho} 
                                              removerDoCarrinho={removerDoCarrinho} 
                                              limparCarrinho={limparCarrinho} 
                                              token={token} 
                                              cliente={clienteLogado} />} 
        />

        {/* Perfil*/}
        <Route path="/perfil" element={<Perfil cliente={clienteLogado} 
                                               token={token}/>} />

        {/*Login */}
       <Route path="/login" element={<Login setClienteLogado={setClienteLogado} 
                                            setToken={setToken} />}
       />
        {/*cadastro*/}
        <Route path="/cadastro" element={<Cadastro />} />
        

      </Routes>
    </BrowserRouter>
  );
}

export default App;