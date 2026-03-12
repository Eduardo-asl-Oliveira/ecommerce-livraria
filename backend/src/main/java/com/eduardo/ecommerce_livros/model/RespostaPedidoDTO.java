package com.eduardo.ecommerce_livros.model;

import java.util.List;

public record RespostaPedidoDTO(Long id, 
                                String nome, 
                                String email, 
                                double total, 
                                StatusPedido status, 
                                List<ItemPedidoDTO> itens) {

}
