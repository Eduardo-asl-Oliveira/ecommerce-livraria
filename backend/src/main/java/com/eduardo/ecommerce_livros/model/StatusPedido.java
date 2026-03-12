package com.eduardo.ecommerce_livros.model;

import org.hibernate.sql.results.graph.entity.EntityResultGraphNode;

public enum StatusPedido {
    AGUARDANDO,
    PAGO,
    ENVIANDO,
    ENTREGUE,
    CANCELADO
}
