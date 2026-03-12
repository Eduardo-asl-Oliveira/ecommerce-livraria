package com.eduardo.ecommerce_livros.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eduardo.ecommerce_livros.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByClienteId(Long clienteId);
}
