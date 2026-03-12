import { Link } from 'react-router-dom';






function Navbar({carrinho}){

    return(
        <div style={{
            display:'flex', //deixa as paradas flexiveis
            justifyContent:'space-between', //empurra para as pontas (espaco entre)
            alignItems:'center', //centraliza
            padding:'15px 10px', //espacamento
            backgroundColor:'#2c3e50', //cor de fundo
            color:'white' //cor da letra
        }}>
            <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
                <h1 style={{ margin: 0, cursor: 'pointer' }}>Livraria Livrar</h1>
            </Link>
            
            <Link to="/carrinho" style={{ color: 'white', textDecoration: 'none', cursor:'pointer' }}>
                <h3>🛒 Carrinho ({carrinho.length})</h3>
            </Link>

            <Link to="/perfil" style={{color:'white'}}>
                <h3> perfil</h3>
            </Link>
        </div>
    )
}export default Navbar;